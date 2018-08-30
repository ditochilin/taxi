package command;

import controller.ControllerHelper;
import entities.Share;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IShareService;
import service.implementation.ShareService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SaveShareCommand extends AbstractCommand<Share> implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(SaveShareCommand.class.getName());
    private static IShareService shareService;

    public SaveShareCommand() {
        service = ShareService.getInstance();
        shareService = ShareService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = request.getParameter("shareId");
            String shareName = ControllerHelper.getParameterInUTF8(request, "shareName");
            Boolean isLoyalty = Boolean.getBoolean(request.getParameter("isLoyalty"));
            Boolean isOnOff = Boolean.getBoolean(request.getParameter("isOnOff"));
            BigDecimal sum = BigDecimal.valueOf(Long.valueOf(request.getParameter("sum")));
            Float percent = Float.valueOf(request.getParameter("percent"));

            Set<String> errors = checkShareFieldsErrors(id, shareName, isLoyalty, sum, percent);

            return doUpdateEntity(new Share(),
                    errors,
                    id,
                    request,
                    response,
                    new EditUserCommand(),
                    new OpenAdministrationCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update user!",e.getCause());
        }
        return Config.getProperty(Config.ADMIN);

    }


    private Set<String> checkShareFieldsErrors(String id, String shareName, Boolean isLoyalty, BigDecimal sum, Float percent) {
        Set<String> errors = new HashSet<>();
        if (shareService.suchShareIsPresent(shareName) && id.isEmpty()) {
            errors.add("Share with name " + shareName + " is present already!");
        }

        if (isLoyalty && shareService.ifLoyaltyDoesAlreadyExist() ) {
            errors.add("Loyalty program is present already! May be the only Loyalty share.");
        }

        if (sum.compareTo(BigDecimal.valueOf(0)) == -1) {
            errors.add("Sum should be positive.");
        }

        if (inRange(0,100, percent)) {
            errors.add("Percent should be in a range: [0..100]!");
        }

        return errors;
    }
}
