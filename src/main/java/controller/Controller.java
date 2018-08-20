package controller;

import command.ICommand;
import session.CookieLogic;
import session.SessionLogic;
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
 * @author Dmitry Tochilin
 */
public class Controller extends HttpServlet {

    private static ControllerHelper controllerHelper = ControllerHelper.getInstance();

    public Controller() {
        super();
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionLogic.print(request,response);//
        CookieLogic.setCookie(response);
        CookieLogic.printCookie(request,response);
        String page = null;
        try {
            ICommand command = controllerHelper.getCommand(request);
            page = command.execute(request, response);
        } catch (ServletException e) {
            page = catchHandler(e,request, Messenger.SERVLET_EXCEPTION);
        } catch (IOException e) {
            page = catchHandler(e,request, Messenger.IO_EXCEPTION);
        }

        //RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        response.sendRedirect(page);
    }

    private String catchHandler(Exception e, HttpServletRequest request, String message){
        //todo delete printStackTrace
        e.printStackTrace();
        request.setAttribute("messageError", Messenger.getProperty(message));
        return Config.getProperty(Config.ERROR);
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Dispatcher servlet";
    }
}
