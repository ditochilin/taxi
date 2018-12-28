package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocale implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String lang = request.getParameter("selectedLang");
        request.getSession().setAttribute("locale", lang);
        //   /Controller/admin?command=openListUsers
        String fromPage = request.getParameter("fromURI");
        String queryString = request.getParameter("queryString");
        return fromPage + "?" + queryString;
    }
}
