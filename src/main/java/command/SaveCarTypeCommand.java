package command;

import controller.ControllerHelper;
import entities.CarType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ICarTypeService;
import service.exceptions.ServiceException;
import service.implementation.CarTypeService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SaveCarTypeCommand extends AbstractCommand<CarType> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(SaveShareCommand.class.getName());
    private static ICarTypeService carTypeService;

    public SaveCarTypeCommand() {
        service = CarTypeService.getInstance();
        carTypeService = CarTypeService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            String idParam = request.getParameter("carTypeId");
            String typeName = ControllerHelper.getParameterInUTF8(request, "typeName");
            BigDecimal price = BigDecimal.valueOf(Long.valueOf(request.getParameter("price")));

            Set<String> errors = checkCarTypeFieldsErrors(idParam, typeName, price);
            Long id = (idParam==null)?Long.valueOf(0):Long.valueOf(idParam);

            return doUpdateEntity(new CarType(id, typeName, price),
                    errors,
                    id,
                    request,
                    response,
                    new EditCarTypeCommand(),
                    new OpenListCarTypesCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update car type!",e.getCause());
        }
        return Config.getProperty(Config.ADMIN);

    }

    private Set<String> checkCarTypeFieldsErrors(String id, String typeName, BigDecimal price) throws ServiceException {
        Set<String> errors = new HashSet<>();
        if (carTypeService.suchCarTypeIsPresent(typeName) && id.isEmpty()) {
            errors.add("CarType with name " + typeName + " is present already!");
        }

        if (price.compareTo(BigDecimal.valueOf(0)) == -1) {
            errors.add("Price should be positive.");
        }
        return errors;
    }
}
