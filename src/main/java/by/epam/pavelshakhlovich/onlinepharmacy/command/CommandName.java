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
    ADD_ITEM_TO_SHOPPING_CART(false, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    ADD_COMPANY(false, UserRole.ADMIN, UserRole.MANAGER),
    ADD_COUNTRY(false, UserRole.ADMIN, UserRole.MANAGER),
    ADD_DOSAGE(false, UserRole.ADMIN, UserRole.MANAGER),
    APPROVE_PRESCRIPTION(false, UserRole.DOCTOR, UserRole.ADMIN),
    CANCEL_ORDER(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    CONFIRM_DELIVERY(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    CONFIRM_PAYMENT(false, UserRole.ADMIN, UserRole.MANAGER),
    CHANGE_LOCALE(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER,UserRole.DOCTOR),
    CLEAR_SHOPPING_CART(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    DELETE_ITEM(false, UserRole.ADMIN, UserRole.MANAGER),
    EDIT_ITEM(false, UserRole.ADMIN, UserRole.MANAGER),
    EDIT_USER(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    LOGIN(false, UserRole.GUEST),
    LOGOUT(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    PAY_ORDER(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    REGISTER(false, UserRole.GUEST, UserRole.ADMIN),
    REQUEST_PRESCRIPTION(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    REMOVE_ITEM_FROM_SHOPPING_CART(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    SEARCH_ITEM(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    SUBMIT_ORDER(false, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    UNKNOWN(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_ADD_ITEM(true, UserRole.MANAGER, UserRole.ADMIN),
    VIEW_EDIT_ITEM(true, UserRole.MANAGER, UserRole.ADMIN),
    VIEW_EDIT_USER(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_ADD_COMPANY(true,  UserRole.MANAGER, UserRole.ADMIN),
    VIEW_ALL_ORDERS(true, UserRole.ADMIN, UserRole.MANAGER),
    VIEW_ALL_USERS(true, UserRole.MANAGER, UserRole.ADMIN),
    VIEW_CATALOG(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_ITEM(true, UserRole.GUEST, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_ORDER(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_ORDERS(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_PRESCRIPTIONS(true, UserRole.ADMIN, UserRole.MANAGER, UserRole.USER, UserRole.DOCTOR),
    VIEW_ALL_PRESCRIPTIONS(true, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_REQUESTED_PRESCRIPTIONS(true, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_SHOPPING_CART(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR),
    VIEW_USER(true, UserRole.USER, UserRole.ADMIN, UserRole.MANAGER, UserRole.DOCTOR);

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
