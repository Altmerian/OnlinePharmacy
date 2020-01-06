package by.epam.pavelshakhlovich.onlinepharmacy.dao;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;

import java.util.List;

/**
 * Represents an interface for retrieving user-related data from data storage, such as database.
 */
public interface UserDao {

    /**
     * Retrieves a user with given login.
     * @param login user's login
     * @return User with corresponding login and other parameters or {@code null} if such user doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    User selectUserByLogin(String login) throws DaoException;

    /**
     * Retrieves a user with given id.
     * @param id user's id
     * @return User with corresponding id and other parameters or {@code null} if such user doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    User selectUserById(long id) throws DaoException;

    /**
     * Retrieves a user with given e-mail.
     * @param email user's login
     * @return User with corresponding e-mail and other parameters or {@code null} if such user doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    User selectUserByEmail(String email) throws DaoException;

    /**
     * Returns a list of users with pagination
     * @param limit is a number of users on the page
     * @return null if no users were found
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<User> selectAllUsers(int offset, int limit) throws DaoException;

    /**
     * Returns number of users in storage
     * @return number of users in storage
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    int countAllUsers() throws DaoException;

    /**
     * Add a new user to the storage, e.g. database with
     * @param user is user bean that should be stored in database
     * @return true if user was added and false if user with such parameters already exists
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean insertUser(User user) throws DaoException;


    boolean updateUser(User user) throws DaoException;

}
