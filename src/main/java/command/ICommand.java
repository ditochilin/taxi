package command;

import dao.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe command contract.
 * @author Dmitry Tochilin
 */
public interface ICommand {

    /**
     * Method execute implemented by each command class.
     * Describes systems behavior on user's events
     * May set attributes, do some settings or checking and returns route to page
     * @param request   - http request
     * @param response  - http response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}

