package command;

import controller.ControllerHelper;
import entities.Order;
import entities.Share;
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
            Set<String> errors = null;

            Order orderDTO = buildOrder(request, errors);

            return doUpdateEntity(orderDTO,
                    errors,
                    orderDTO.getId(),
                    request,
                    response,
                    new EditShareCommand(),
                    new OpenListSharesCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update share!", e.getCause());
        }
        return Config.getProperty(Config.ORDERS);
    }

    private static Order buildOrder(HttpServletRequest request, Set<String> errors) throws ServiceException {

        String idParam = request.getParameter("orderId");
        Long id = (idParam == null) ? Long.valueOf(0) : Long.valueOf(idParam);
        Status status = Status.valueOf(request.getParameter("statusName"));
        String dateTime = request.getParameter("dateTime");
        Long clientId = Long.valueOf(request.getParameter("clientId"));
        Long carTypeId = Long.valueOf(request.getParameter("carTypeId"));
        String startPoint = ControllerHelper.getParameterInUTF8(request, "startPoint");
        String endPoint = ControllerHelper.getParameterInUTF8(request, "endPoint");
        Long taxiId = Long.valueOf(request.getParameter("taxiId"));

        Long loyaltyId = Long.valueOf(request.getParameter("loyaltyId"));
        Long shareId = Long.valueOf(request.getParameter("shareId"));

        Integer discount = Integer.valueOf(request.getParameter("discount"));

//        //auto counted
//        BigDecimal cost = BigDecimal.valueOf(Long.valueOf(request.getParameter("cost")));

        String feedTime = request.getParameter("feedTime");
        Integer waitingTime = Integer.valueOf(request.getParameter("waitingTime"));

        Order orderDTO = new Order();
        checkOrderFieldsErrorsAndFulFill(orderDTO, id, status,
                dateTime, clientId, carTypeId,
                startPoint, endPoint,
                taxiId, discount, feedTime, waitingTime, errors);

        addShares(orderDTO, loyaltyId, shareId);

        return orderDTO;
    }

    private static void addShares(Order orderDTO, Long ...sharesIds) throws ServiceException {
        for (Long shareId:sharesIds
             ) {
            if(shareId!=null) {
                orderDTO.getShares().add(
                        shareService.getById(shareId)
                );
            }
        }
    }

    private static void checkOrderFieldsErrorsAndFulFill(Order orderDTO, Long id, Status status, String dateTime,
                                                         Long clientId, Long carTypeId, String startPoint,
                                                         String endPoint, Long taxiId, Integer discount,
                                                         String feedTime, Integer waitingTime, Set<String> errors) {
        if (id == null) {
            orderDTO.setStatus(Status.CREATED);
        } else if (taxiId != null && Status.CREATED.equals(status)) {
            orderDTO.setStatus(Status.INWORK);
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ROOT);
        Date newDateTime = new Date();
        if (id != null || !dateTime.isEmpty()) {
            try {
                newDateTime = dateformat.parse(dateTime);
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

        if (feedTime.isEmpty()) {
            errors.add("Feed time must be!");
        } else {
            try {
                orderDTO.setFeedTime(dateformat.parse(feedTime));
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
