package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Contains names and paths of all possible jsp pages
 */
public enum JspPage {
    ROOT("/"),
    INDEX("/index.jsp"),
    MAIN("/jsp/main.jsp"),
    LOGIN("/jsp/login.jsp"),
    REGISTER("/register"),
    ERROR("/jsp/error.jsp"),
    ADD_ITEM("/WEB-INF/jsp/catalog/add-item.jsp"),
    SEARCH_ITEM("/WEB-INF/jsp/search-item.jsp"),
    EDIT_ITEM("/WEB-INF/jsp/catalog/edit-item.jsp"),
    EDIT_USER("/WEB-INF/jsp/users/edit-profile.jsp"),
    GUEST_HEADER("/WEB-INF/jsp/headers/guest-header.jsp"),
    USER_HEADER("/WEB-INF/jsp/headers/user-header.jsp"),
    ADMIN_HEADER("/WEB-INF/jsp/headers/admin-header.jsp"),
    DOCTOR_HEADER("/WEB-INF/jsp/headers/doctor-header.jsp"),
    MANAGER_HEADER("/WEB-INF/jsp/headers/admin-header.jsp"),
    VIEW_ITEM("/WEB-INF/jsp/catalog/view-item.jsp"),
    VIEW_CATALOG("/WEB-INF/jsp/catalog/view-catalog.jsp"),
    VIEW_ORDER("/WEB-INF/jsp/orders/view-order.jsp"),
    VIEW_ORDERS("/WEB-INF/jsp/orders/view-orders.jsp"),
    VIEW_ALL_ORDERS("/WEB-INF/jsp/orders/view-all-orders.jsp"),
    VIEW_SHOPPING_CART("/WEB-INF/jsp/orders/view-shopping-cart.jsp"),
    VIEW_ALL_USERS("/WEB-INF/jsp/users/view-all-users.jsp"),
    VIEW_USER("/WEB-INF/jsp/users/view-profile.jsp");

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
