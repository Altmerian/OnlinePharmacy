package by.epam.pavelshakhlovich.onlinepharmacy.controller;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(TestServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.info(getClass().getSimpleName() + " has been initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setStatus(200);
//        response.setContentType("text/html");
//        PrintWriter printWriter = response.getWriter();
//        printWriter.println("<html><h1>Hello world!<h1></html>");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (cart == null ) {
            cart = new Cart();
            cart.setName(name);
            cart.setQuantity(quantity);
        }
        session.setAttribute("cart", cart);

        request.getRequestDispatcher("/cart").forward(request, response);
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
