package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Contains names and paths of all possible jsp pages
 */
public enum JspPage {
    ROOT("/"),
    INDEX("/index.jsp"),
    MAIN("/WEB-INF/jsp/main.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    ERROR("/WEB-INF/jsp/error.jsp"),
    SHOPPING_CART("/WEB-INF/jsp/shopping-cart.jsp");

    private String path;

    JspPage(String path) {
        this.path = path;
    }

    /**
     * @return path to jsp page
     */
    public String getPath() {
        return path;
    }

}
