package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.UserDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.UserDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.UserService;
import by.epam.pavelshakhlovich.onlinepharmacy.util.Hasher;
import by.epam.pavelshakhlovich.onlinepharmacy.util.Validator;
import by.epam.pavelshakhlovich.onlinepharmacy.util.SaltGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Represents a class implementation of a {@see UserService} interface.
 */
public class UserServiceImpl implements UserService {
    private static UserDao userDao = new UserDaoSQLImpl();
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public User loginUser(String login, String password) throws ServiceException {
        User user;
        try {
            user = userDao.selectByLogin(login);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException("Can't get data from DAO layer", e));
        }
        if (user != null) {
            String saltedPassword = user.getSalt() + password;
            try {
                if (!Hasher.md5Hash(saltedPassword).equals(user.getHashedPassword())) {
                    return null;
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                LOGGER.throwing(Level.ERROR, new ServiceException("Can't get md5 hash", e));
            }
        }
        return user;
    }


    @Override
    public boolean registerUser(User user) throws ServiceException {
        if (!Validator.isUserValid(user)){
            return false;
        }
        boolean isNotExist = false;
        try {
            if (userDao.selectByEmail(user.getEmail()) == null &&
                    userDao.selectByLogin(user.getLogin()) == null) {
                isNotExist = true;
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException("Can't get data from DAO layer", e));
        }
        boolean result;
        if (isNotExist) {
            try {
                user.setSalt(SaltGenerator.getInstance().getSalt());
                String hashedPassword = Hasher.md5Hash(user.getSalt() + user.getPassword());
                user.setHashedPassword(hashedPassword);
                user.setRole(UserRole.USER);
                result = userDao.create(user);
            } catch (DaoException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
                throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
            }
            return result;
        } else {
            return false;
        }

    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
        try {
            if (user.getPassword() != null) {
                String hashedPassword = Hasher.md5Hash(user.getSalt() + user.getPassword());
                user.setHashedPassword(hashedPassword);
            }
            return userDao.update(user);
        } catch (DaoException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public List<User> selectAllUsers(int offset, int limit) throws ServiceException {
        try {
            return userDao.selectAll(offset, limit);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public int countAllUsers() throws ServiceException {
        try {
            return userDao.countAllUsers();
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public User selectUserById(User user, long userId) throws ServiceException {
        if (user.getId() == userId) {
            return user;
        } else {
            if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER) {
                try {
                    return userDao.selectById(userId);
                } catch (DaoException e) {
                    throw LOGGER.throwing(Level.ERROR, new ServiceException("Can't get data from DAO layer", e));
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public User selectUserByEmail(User user, String email) throws ServiceException {
        if (user.getEmail().equals(email)) {
            return user;
        } else {
            if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER) {
                try {
                    return userDao.selectByEmail(email);
                } catch (DaoException e) {
                    throw LOGGER.throwing(Level.ERROR, new ServiceException("Can't get data from DAO layer", e));
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public User selectUserByLogin(User user, String login) throws ServiceException {
        if (user.getLogin().equals(login)) {
            return user;
        } else {
            if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER) {
                try {
                    return userDao.selectByLogin(login);
                } catch (DaoException e) {
                    throw LOGGER.throwing(Level.ERROR, new ServiceException("Can't get data from DAO layer", e));
                }
            } else {
                return null;
            }
        }
    }

}
