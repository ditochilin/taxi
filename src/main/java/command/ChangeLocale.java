package command;

import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocale implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String lang = request.getParameter("selectedLang");
        request.getSession().setAttribute("locale", lang);
        String fromPage = request.getParameter("fromURI");
        return  fromPage;
    }
}
