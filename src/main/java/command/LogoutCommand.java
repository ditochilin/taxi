package command;

import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command for logout user. Cleans session's attributes, makes redirect
 * @author Dmitry Tochilin
 */
public class LogoutCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        if(session!=null){
            session.removeAttribute("user");
            session.removeAttribute("locale");
            session.removeAttribute("role");
        }

        return Config.getProperty(Config.LOGIN);
    }
}
