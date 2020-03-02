package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.UserDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.UserDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private UserService userService;
    private UserDao userDaoMock;

    private User user1;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user1 = new User();
        user1.setId(1L);
        user1.setLogin("User1");
        user1.setPassword("sadklfgh");
        user1.setEmail("sdf@sfg.ru");
        user1.setRole(UserRole.ADMIN);
        userDaoMock = mock(UserDao.class);
        setMock(userDaoMock);
        userService = new UserServiceImpl();
    }

    private void setMock(UserDao mock) {
        try {
            Field instance = UserServiceImpl.class.getDeclaredField("userDao");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw LOGGER.throwing(Level.ERROR, new RuntimeException(e));
        }
    }


    @AfterClass
    public void tearDown() {
        userService = null;
        user1 = null;
        userDaoMock = null;
        try {
            Field instance = UserServiceImpl.class.getDeclaredField("userDao");
            instance.setAccessible(true);
            instance.set(instance, new UserDaoSQLImpl());
        } catch (Exception e) {
            throw LOGGER.throwing(Level.ERROR, new RuntimeException(e));
        }

    }

    @Test
    public void testRegisterUser() throws DaoException, ServiceException {
        when(userDaoMock.create(any(User.class))).thenReturn(true);
        when(userDaoMock.selectByLogin(user1.getLogin())).thenReturn(null);
        when(userDaoMock.selectByEmail(user1.getEmail())).thenReturn(null);
        assertTrue(userService.registerUser(user1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testThrowingExceptionWhenDAOLayerError() throws DaoException, ServiceException {
        Mockito.when(userDaoMock.countAllUsers()).thenThrow(new DaoException());
        userService.countAllUsers();
    }

    @Test
    public void testSelectUserById() throws DaoException, ServiceException {
        when(userDaoMock.selectById(1L)).thenReturn(user1);
        User expected = user1;
        assertEquals(userService.selectUserById(user1, 1L), expected);
    }
}