package integratedTests.controller;

import controller.Controller;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ControllerTest {

    private static final String testPage = "/index.jsp";

    @Test
    public void startApplicationTestIdexPage() throws ServletException, IOException {
        Controller controller = new Controller();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(testPage)).thenReturn(dispatcher);

        controller.service(request, response);

        verify(request, times(2)).getAttribute("redirect");
//        verify(request, timeout(1)).getRequestDispatcher(testPage);
//        verify(request, never()).getSession();
//        verify(dispatcher).forward(request, response);
    }

}
