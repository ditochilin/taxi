package command;

import controller.ControllerHelper;
import entities.Share;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.IShareService;
import service.exceptions.ServiceException;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        try {
            String idParam = request.getParameter("shareId");
            String shareName = ControllerHelper.getParameterInUTF8(request, "shareName");
            Boolean isLoyalty = "on".equals(request.getParameter("isLoyalty"));
            Boolean isOnOff = "on".equals(request.getParameter("isOnOff"));
            BigDecimal sum = BigDecimal.valueOf(Long.valueOf(request.getParameter("sum")));
            Float percent = Float.valueOf(request.getParameter("percent"));

            Set<String> errors = checkShareFieldsErrors(idParam, shareName, isLoyalty, sum, percent);
            Long id = (idParam==null)?Long.valueOf(0):Long.valueOf(idParam);

            return doUpdateEntity(new Share(id, shareName,isLoyalty,isOnOff,sum,percent),
                    errors,
                    id,
                    request,
                    response,
                    new EditShareCommand(),
                    new OpenListSharesCommand());
        } catch (ServletException | IOException e) {
            LOGGER.error("Could not execute command to add/update share!",e.getCause());
        }
        return Config.getProperty(Config.ADMIN);

    }


    private Set<String> checkShareFieldsErrors(String id, String shareName, Boolean isLoyalty, BigDecimal sum, Float percent) {
        Set<String> errors = new HashSet<>();
        try {
            if (shareService.suchShareIsPresent(shareName) && id.isEmpty()) {
                errors.add("Share with name " + shareName + " is present already!");
            }

            if (isLoyalty && shareService.ifLoyaltyDoesAlreadyExist(shareName)) {
                errors.add("Loyalty program is present already! May be the only Loyalty share.");
            }

            if (sum.compareTo(BigDecimal.valueOf(0)) == -1) {
                errors.add("Sum should be positive.");
            }

            if (!inRange(0, 100, percent)) {
                errors.add("Percent should be in a range: [0..100]!");
            }
        }catch (Exception e){
            errors.add(e.getMessage());
            LOGGER.error(e.getCause());
        }
        return errors;
    }
}
