package command;

import controller.ControllerHelper;
import entities.Taxi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ICarTypeService;
import service.ITaxiService;
import service.IUserService;
import service.exceptions.ServiceException;
import service.implementation.CarTypeService;
import service.implementation.TaxiService;
import service.implementation.UserService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SaveTaxiCommand extends AbstractCommand<Taxi> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(SaveTaxiCommand.class.getName());
    private static ITaxiService taxiService;
    private static ICarTypeService carTypeService;
    private static IUserService userService;

    public SaveTaxiCommand() {
        service = TaxiService.getInstance();
        taxiService = TaxiService.getInstance();
        carTypeService = CarTypeService.getInstance();
        userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            Set<String> errors = new HashSet<>();
            Taxi taxiDTO = buildTaxi(request, errors);

            return doUpdateEntity(taxiDTO,
                    errors,
                    taxiDTO.getId(),
                    request,
                    response,
                    new EditTaxiCommand(),
                    new OpenListTaxisCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update taxi!", e.getCause());
        }
        return Config.getProperty(Config.TAXIS);

    }

    private Taxi buildTaxi(HttpServletRequest request, Set<String> errors) {

        Long id = getLongParameter(request,"taxiId");
        Long driverId = getLongParameter(request,"driverId");
        Long carTypeId = getLongParameter(request,"carTypeId");
        String carName = ControllerHelper.getParameterInUTF8(request, "carName");
        String carNumber = ControllerHelper.getParameterInUTF8(request, "carNumber");
        boolean busy = "on".equals(request.getParameter("busy"));

        return checkOrderFieldsErrorsAndFulFill(id, driverId,
                carTypeId, carName, carNumber, busy, errors);

    }

    private Taxi checkOrderFieldsErrorsAndFulFill(Long id, Long driverId, Long carTypeId, String carName, String carNumber,
                                                  boolean busy, Set<String> errors) {

        Taxi taxiDTO = new Taxi();
        taxiDTO.setId(id);

        if (carTypeId == null) {
            errors.add("CarType must be in order!");
        } else {
            try {
                taxiDTO.setCarType(carTypeService.getById(carTypeId));
            } catch (ServiceException e) {
                errors.add("Could not get car type by id: " + carTypeId);
            }
        }

        if (driverId == null) {
            errors.add("Driver must be in order!");
        } else {
            try {
                taxiDTO.setDriver(userService.getById(driverId));
            } catch (ServiceException e) {
                errors.add("Could not get client by id: " + driverId);
            }
        }

        if (carName.isEmpty()) {
            errors.add("Car name must be!");
        } else {
            taxiDTO.setCarName(carName);
        }

        if (carNumber.isEmpty()) {
            errors.add("State's number of car must be!");
        } else {
            taxiDTO.setCarNumber(carNumber);
        }

        taxiDTO.setBusy(busy);

        return taxiDTO;
    }
}
