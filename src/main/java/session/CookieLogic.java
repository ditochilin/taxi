package session;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dmitry Tochilin
 */

//  todo check if this is needed??
public class CookieLogic {

    public static void setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("Terminator", "MegaAdmin");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    public static void printCookie(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            Cookie[] cookie = request.getCookies();
            if (cookie != null) {
                out.print("length" + cookie.length + "<BR>");
                for (int i = 0; i < cookie.length; i++) {
                    Cookie c = cookie[i];
                    out.print(c.getName() + " = " + c.getValue());
                }
            }
            out.close();
        } catch (IOException ex) {
            //todo  delete printStackTrace
            ex.printStackTrace();
            throw new RuntimeException(ex.toString());
        }


    }
}
