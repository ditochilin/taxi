package command;

import service.ITaxiService;
import service.implementation.TaxiService;
import utils.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenListTaxisCommand implements ICommand {

    private static ITaxiService taxiService = TaxiService.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("taxiList",taxiService.getAll());

        return Config.getProperty(Config.TAXIS);
    }
}
