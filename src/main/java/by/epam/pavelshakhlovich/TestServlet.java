package by.epam.pavelshakhlovich;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(TestServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.info(getClass().getSimpleName() + " has been initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(200);
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<html><h1>Hello world!<h1></html>");
        LOGGER.info("request has been handled");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
        LOGGER.info(getClass().getSimpleName() + " has been destroyed.");
    }
}
