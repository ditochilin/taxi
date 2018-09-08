package controller;

import command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exceptions.ServiceException;
import utils.Config;
import utils.Messenger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet dispatcher. Handle all http traffic
 *
 * @author Dmitry Tochilin
 */
public class Controller extends HttpServlet {

    private static ControllerHelper controllerHelper = ControllerHelper.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(Controller.class.getName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = "";
        String redirect = (String) request.getAttribute("redirect");
        try {
            if (redirect != null) {
                page = redirect;
            } else {
                ICommand command = controllerHelper.getCommand(request);
                page = command.execute(request, response);
            }
            LOGGER.info("Servlet forward to page " + page);
        } catch (ServletException e) {
            page = catchHandler(e, request, Messenger.SERVLET_EXCEPTION);
        } catch (IOException e) {
            page = catchHandler(e, request, Messenger.IO_EXCEPTION);
        } catch (ServiceException e) {
            page = catchHandler(e, request, e.getMessage());
        }
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            response.sendError(404, "no such page or command");
        }
    }

    private String catchHandler(Exception e, HttpServletRequest request, String message) {
        LOGGER.error(message);
        request.setAttribute("errorDescription", Messenger.getProperty(message));
        return Config.getProperty(Config.ERROR);
    }

}
