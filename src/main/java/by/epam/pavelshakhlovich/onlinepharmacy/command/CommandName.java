package by.epam.pavelshakhlovich.onlinepharmacy.command;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains names of all classes that implements {@see Command} interface
 */
public enum CommandName {
    ADD_ITEM(false, UserRole.MANAGER, UserRole.ADMIN),
    ADD_ITEM_TO_ORDER(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    ADD_ITEM_TO_SHOPPING_CART(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER),
    CHANGE_LOCALE(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER,UserRole.DOCTOR),
    CLEAR_SHOPPING_CART(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER),
    LOGIN(false, UserRole.GUEST),
    LOGOUT(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    REGISTER(false, UserRole.GUEST),
    UNKNOWN(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR);

    private boolean isGetAllowed;
    private List<UserRole> rolesAllowed = new ArrayList<>();

    CommandName(boolean isGetAllowed, UserRole... rolesAllowed) {
        this.isGetAllowed = isGetAllowed;
        this.rolesAllowed.addAll(Arrays.asList(rolesAllowed));
    }

    /**
     * Defines whether get method is allowed to request the command
     *
     * @return true if get method is allowed
     */
    public boolean isGetAllowed() {
        return isGetAllowed;
    }

    /**
     * Defines whether a user with it's specified role is allowed to perform the command
     *
     * @param role user's role
     * @return true if user is allowed to perform the command
     */
    public boolean isRoleAllowed(UserRole role) {
        return rolesAllowed.contains(role);
    }
    }
