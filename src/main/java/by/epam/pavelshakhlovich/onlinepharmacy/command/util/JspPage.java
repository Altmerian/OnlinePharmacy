package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Contains names and paths of all possible jsp pages
 */
public enum JspPage {
    ROOT("/"),
    ADD_ITEM("/WEB-INF/jsp/catalog/add-item.jsp"),
    ADMIN_HEADER("/WEB-INF/jsp/headers/admin-header.jsp"),
    COMMON_HEADER("/WEB-INF/jsp/headers/common-header.jsp"),
    DOCTOR_HEADER("/WEB-INF/jsp/headers/doctor-header.jsp"),
    ERROR("/WEB-INF/jsp/error.jsp"),
    GUEST_HEADER("/WEB-INF/jsp/headers/guest-header.jsp"),
    INDEX("/index.jsp"),
    LOGIN("/WEB-INF/jsp/user/login.jsp"),
    MANAGER_HEADER("/WEB-INF/jsp/headers/admin-header.jsp"),
    MAIN("/WEB-INF/jsp/main.jsp"),
    REGISTER("/WEB-INF/jsp/user/register.jsp"),
    SHOPPING_CART("/WEB-INF/jsp/shopping-cart.jsp"),
    USER_HEADER("/WEB-INF/jsp/headers/user-header.jsp");

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
