package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Contains names and paths of all possible jsp pages
 */
public enum JspPage {
    ROOT("/"),
    INDEX("/index.jsp"),
    MAIN("/jsp/main.jsp"),
    LOGIN("/jsp/login.jsp"),
    ERROR("/jsp/error.jsp"),
    SHOPPING_CART("/jsp/shopping-cart.jsp");

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
