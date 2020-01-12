package by.epam.pavelshakhlovich.onlinepharmacy.dao;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;

/**
 * Represents an interface for retrieving user-related data from data storage, such as database.
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Retrieves a user with given login.
     * @param login user's login
     * @return User with corresponding login and other parameters or {@code null} if such user doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    User selectByLogin(String login) throws DaoException;

    /**
     * Retrieves a user with given e-mail.
     * @param email user's login
     * @return User with corresponding e-mail and other parameters or {@code null} if such user doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    User selectByEmail(String email) throws DaoException;

    /**
     * Returns number of users in storage
     * @return number of users in storage
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    int countAllUsers() throws DaoException;

}
