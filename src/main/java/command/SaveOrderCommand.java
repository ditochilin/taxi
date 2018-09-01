package command;

import controller.ControllerHelper;
import entities.Order;
import entities.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.*;
import service.exceptions.ServiceException;
import service.implementation.*;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaveOrderCommand extends AbstractCommand<Order> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(SaveOrderCommand.class.getName());
    private static IOrderService orderService;
    private static IUserService userService;
    private static ICarTypeService carTypeService;
    private static ITaxiService taxiService;
    private static IShareService shareService;

    public SaveOrderCommand() {
        orderService = OrderService.getInstance();
        userService = UserService.getInstance();
        carTypeService = CarTypeService.getInstance();
        taxiService = TaxiService.getInstance();
        shareService = ShareService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            Set<String> errors = new HashSet<>();

            Order orderDTO = buildOrder(request, errors);

            return doUpdateEntity(orderDTO,
                    errors,
                    orderDTO.getId(),
                    request,
                    response,
                    new EditOrderCommand(),
                    new OpenListOrdersCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update share!", e.getCause());
        }
        return Config.getProperty(Config.ORDERS);
    }

    // todo : make patter Builder
    private static Order buildOrder(HttpServletRequest request, Set<String> errors) throws ServiceException {

        Long id = getLongParameter(request,"orderId");
        Status status = Status.valueOf(request.getParameter("statusName"));
        String dateOrder = request.getParameter("dateOrder");
        String timeOrder = request.getParameter("timeOrder");
        Long clientId = getLongParameter(request,"clientId");
        Long carTypeId = getLongParameter(request,"carTypeId");
        String startPoint = ControllerHelper.getParameterInUTF8(request, "startPoint");
        String endPoint = ControllerHelper.getParameterInUTF8(request, "endPoint");
        Long taxiId = getLongParameter(request,"taxiId");

        Long loyaltyId = getLongParameter(request,"loyaltyId");
        Long shareId = getLongParameter(request,"shareId");

        Integer discount = Integer.valueOf(request.getParameter("discount"));

//        //auto counted
//        BigDecimal cost = BigDecimal.valueOf(Long.valueOf(request.getParameter("cost")));

        String dateFeed = request.getParameter("dateFeed");
        String timeFeed = request.getParameter("timeFeed");
        Integer waitingTime = Integer.valueOf(request.getParameter("waitingTime"));

        Order orderDTO = new Order();
        checkOrderFieldsErrorsAndFulFill(orderDTO, id, status,
                dateOrder, timeOrder, clientId, carTypeId,
                startPoint, endPoint,
                taxiId, discount, dateFeed, timeFeed, waitingTime, errors);

        addShares(orderDTO, loyaltyId, shareId);

        return orderDTO;
    }

    private static Long getLongParameter(HttpServletRequest request, String name){
        String value = request.getParameter("clientId");
        if(value==null){
            return null;
        }
        return Long.valueOf(value);
    }

    private static void addShares(Order orderDTO, Long... sharesIds) throws ServiceException {
        for (Long shareId : sharesIds) {
            if (shareId != null) {
                orderDTO.getShares().add(
                        shareService.getById(shareId)
                );
            }
        }
    }

    private static void checkOrderFieldsErrorsAndFulFill(Order orderDTO, Long id, Status status, String dateOrder, String timeOrder,
                                                         Long clientId, Long carTypeId, String startPoint,
                                                         String endPoint, Long taxiId, Integer discount,
                                                         String dateFeed, String timeFeed, Integer waitingTime, Set<String> errors) {
        if (id == null) {
            orderDTO.setStatus(Status.CREATED);
        } else if (taxiId != null && Status.CREATED.equals(status)) {
            orderDTO.setStatus(Status.INWORK);
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ROOT);
        Date newDateTime = new Date();
        if (id != null || (!dateOrder.isEmpty() && !timeOrder.isEmpty())) {
            try {
                newDateTime = dateformat.parse(dateOrder + " " + timeOrder);
            } catch (ParseException e) {
                errors.add("Date & Time couldn't be parsed! " + e.getMessage());
            }
        }

        orderDTO.setDateTime(newDateTime);

        if (clientId == null) {
            errors.add("Client must be in order!");
        } else {
            try {
                orderDTO.setClient(userService.getById(Long.valueOf(clientId)));
            } catch (ServiceException e) {
                errors.add("Could not get client by id: " + clientId);
            }
        }

        if (carTypeId == null) {
            errors.add("CarType must be in order!");
        } else {
            try {
                orderDTO.setCarType(carTypeService.getById(Long.valueOf(carTypeId)));
            } catch (ServiceException e) {
                errors.add("Could not get client by id: " + clientId);
            }
        }

        //  taxi must be if in work
        if ((Status.INWORK.equals(status)) && taxiId == null) {
            errors.add("Taxi must be in order!");
        } else if (taxiId != null) {
            try {
                orderDTO.setTaxi(taxiService.getById(Long.valueOf(taxiId)));
            } catch (ServiceException e) {
                errors.add("Could not get client by id: " + clientId);
            }
        }

        if (startPoint.isEmpty()) {
            errors.add("Start point of root must be!");
        } else {
            orderDTO.setStartPoint(startPoint);
        }

        if (endPoint.isEmpty()) {
            errors.add("Destination of root must be!");
        } else {
            orderDTO.setEndPoint(endPoint);
        }

        if (discount < 0) {
            errors.add("Discount must be positive!");
        } else if (discount > 0) {
            orderDTO.setDiscount(discount);
        }

        if (dateFeed.isEmpty() || timeFeed.isEmpty()) {
            errors.add("Feed time must be!");
        } else {
            try {
                Date newFeedTime = dateformat.parse(dateFeed + " " + timeFeed+":00");
                orderDTO.setFeedTime(newFeedTime);
            } catch (ParseException e) {
                errors.add("Feed Time couldn't be parsed! " + e.getMessage());
            }
        }

        if (waitingTime < 0) {
            errors.add("Waiting time must be positive!");
        } else if (waitingTime > 0) {
            orderDTO.setWaitingTime(waitingTime);
        }
    }
}
