package command;

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
     *
     * @param request   - http request
     * @param responce  - http response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    String execute(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException;

}

