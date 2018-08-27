package filter;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EncodingFilter implements Filter {

    private static final String ENCODING_UTF8 = StandardCharsets.UTF_8.name();

    private static void encodingSetter(ServletRequest request, ServletResponse response) {

        if (response.getCharacterEncoding() == null || !response.getCharacterEncoding().equals(ENCODING_UTF8)) {
            response.setCharacterEncoding(ENCODING_UTF8);
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        encodingSetter(request, response);
        filterChain.doFilter(request, response);
    }
}