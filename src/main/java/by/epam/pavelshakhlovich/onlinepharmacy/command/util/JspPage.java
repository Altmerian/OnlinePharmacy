package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Contains names and paths of all possible jsp pages
 */
public enum JspPage {
    ADD_COMPANY("/add-company"),
    ADD_ITEM("/add-item"),
    EDIT_ITEM("/edit-item"),
    ADMIN_HEADER("/WEB-INF/jsp/headers/admin-header.jsp"),
    DOCTOR_HEADER("/WEB-INF/jsp/headers/doctor-header.jsp"),
    ERROR("/error"),
    GUEST_HEADER("/WEB-INF/jsp/headers/guest-header.jsp"),
    INDEX("/index.jsp"),
    LOGIN("/login"),
    MANAGER_HEADER("/WEB-INF/jsp/headers/admin-header.jsp"),
    MAIN("/main"),
    REGISTER("/register"),
    SHOPPING_CART("/shopping-cart"),
    USER_HEADER("/WEB-INF/jsp/headers/user-header.jsp"),
    VIEW_CATALOG("/view-catalog"),
    VIEW_ORDER("/view-order"),
    VIEW_ORDERS("/view-orders"),
    VIEW_ALL_ORDERS("/view-all-orders"),
    VIEW_ITEM ("/view-item");

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
