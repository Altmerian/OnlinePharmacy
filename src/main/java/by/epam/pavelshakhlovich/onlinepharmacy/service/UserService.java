package by.epam.pavelshakhlovich.onlinepharmacy.service;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;

import java.util.List;

/**
 * Represents an interface of a service for user-related actions
 */
public interface UserService {

    /**
     * Attempts to authenticate and authorize a user with a given login and password
     * @param login user's login
     * @param password user's password
     * @return {@see User} object with id and role or null, if credentials are invalid
     * @throws ServiceException if DaoException occurred
     */
    User loginUser(String login, String password) throws ServiceException;

    /**
     * Attempts to register a new user with given personal info. Login  and email should be unique in the system
     * @param user user with given parameters
     * @return true if registration succeeded, false if user with such login or email already exists
     * @throws ServiceException if DaoException occurred
     */
    boolean registerUser(User user) throws ServiceException;

    /**
     * Updates user data
     * @param user is user with updated data
     * @return true if status successfully changed
     * @throws ServiceException if DaoException occurred
     */
    boolean updateUser(User user) throws ServiceException;

    /**
     * Returns a list of all users
     * @return null if no users were found
     * @throws ServiceException if DaoException occurred
     */
    List<User> selectAllUsers() throws ServiceException;

    /**
     * Returns a list of users with pagination
     * @return number of users in storage
     * @throws ServiceException if DaoException occurred
     */
    int countAllUsers() throws ServiceException;

    /**
     * Returns a User with given id
     * @param user is user that requests
     * @param userId is id of requested user
     * @return null if no such user in the storage
     * @throws ServiceException if DaoException occurred
     */
    User selectUserById(User user, long userId) throws ServiceException;
    /**
     * Returns a User with given email
     * @param user is user that requests
     * @param email is email of requested user
     * @return null if no such user in the storage
     * @throws ServiceException if DaoException occurred
     */
    User selectUserByEmail(User user, String email) throws ServiceException;

    /**
     * Returns a User with given login
     * @param user is user that requests
     * @param login is login of requested user
     * @return null if no such user in the storage
     * @throws ServiceException if DaoException occurred
     */
    User selectUserByLogin(User user, String login) throws ServiceException;
}
