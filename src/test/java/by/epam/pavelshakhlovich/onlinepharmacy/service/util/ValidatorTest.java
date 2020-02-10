package by.epam.pavelshakhlovich.onlinepharmacy.service.util;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class ValidatorTest {
    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void setUp() {
        validUser = new User();
        validUser.setLogin("validUser");
        validUser.setPassword("sadklfgh");
        validUser.setEmail("sdf@sfg.ru");
        invalidUser = new User();
        invalidUser.setLogin("User1");
        invalidUser.setPassword("sadklfgh");
        invalidUser.setEmail("sdf-g.@ru");
    }

    @DataProvider(name = "validLogin")
    public Object[][] provideValidLogin() {
        return new Object[][]{{"login"}, {"Login1"}, {"kUuysdf23"}};
    }

    @DataProvider(name = "invalidLogin")
    public Object[][] provideInvalidLogin() {
        return new Object[][]{{"user"}, {"user,-="},{"f34"}};
    }

    @DataProvider(name = "validPassword")
    public Object[][] provideValidPassword() {
        return new Object[][]{{"fghfgh"},{"p-*/32jk"},{"2340-="}};
    }

    @DataProvider(name = "invalidPassword")
    public Object[][] provideInvalidPassword() {
        return new Object[][]{{"pas"},{"123"},{""}};
    }

    @DataProvider(name = "validEmail")
    public Object[][] provideValidEmail() {
        return new Object[][]{{"sdf@df.bg"}, {"test@tsd.ada"}, {"2t@asd.sdf"}};
    }

    @DataProvider(name = "invalidEmail")
    public Object[][] provideInvalidEmail() {
        return new Object[][]{{"invalid"}, {"asdfsdf.com"},{"dfsfs@com"}};
    }

    @Test(dataProvider = "validLogin")
    public void testIsLoginValidPositive(String login) {
        assertTrue(Validator.isLoginValid(login));
    }

    @Test(dataProvider = "invalidLogin")
    public void testIsLoginValidNegative(String login) {
        assertFalse(Validator.isLoginValid(login));
    }

    @Test(dataProvider = "validPassword")
    public void testIsPasswordValidPositive(String password) {
        assertTrue(Validator.isPasswordValid(password));
    }

    @Test(dataProvider = "invalidPassword")
    public void testIsPasswordValidNegative(String password) {
        assertFalse(Validator.isPasswordValid(password));
    }

    @Test(dataProvider = "validEmail")
    public void testIsEmailValidPositive(String email) {
        assertTrue(Validator.isEmailValid(email));
    }

    @Test(dataProvider = "invalidEmail")
    public void testIsEmailValidNegative(String email) {
        assertFalse(Validator.isEmailValid(email));
    }

    @Test
    public void testIsUserValidPositive() {
        assertTrue(Validator.isUserValid(validUser));
    }

    @Test
    public void testIsUserValidNegative() {
        assertFalse(Validator.isUserValid(invalidUser));
    }
}