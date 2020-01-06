package by.epam.pavelshakhlovich.onlinepharmacy.filter;

import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandName;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters all requests to the servlet by checking whether the user has corresponding rights to execute
 * a specified command and whether get method is appropriate for the command.
 * If rights are insufficient, request is redirected to the root page.
 */
@WebFilter(urlPatterns = {"/Controller"})
public class SecurityFilter implements Filter {


    private static final String GET = "get";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute(Parameter.USER);
        UserRole role;
        if (user == null) {
            role = UserRole.GUEST;
        } else {
            role = user.getRole();
        }
        String command = request.getParameter(Parameter.COMMAND);
        if (command != null && !command.isEmpty()) {
            CommandName commandName;
            try {
                commandName = CommandName.valueOf(command.replace("-", "_").toUpperCase());
            } catch (IllegalArgumentException e) {
                response.sendRedirect(JspPage.ROOT.getPath());
                return;
            }
            if (!commandName.isRoleAllowed(role) ||
                    (!commandName.isGetAllowed() && request.getMethod().equalsIgnoreCase(GET))) {
                response.sendRedirect(JspPage.ROOT.getPath());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
