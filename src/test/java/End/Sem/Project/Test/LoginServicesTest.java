package End.Sem.Project.Test;

import End.Sem.Project.Dao.LoginDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Dao.UserMappingDao;
import End.Sem.Project.Model.Login;
import End.Sem.Project.Services.LoginServices;
import End.Sem.Project.DTO.loginDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;  // Import this extension

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Ensure that Mockito initializes the mocks
class LoginServicesTest {

    @Mock
    private LoginDao loginDao;

    @Mock
    private UserDao userDao;

    @Mock
    private UserMappingDao umDao;

    @InjectMocks
    private LoginServices loginServices;  // LoginServices will have mocks injected

    private loginDTO loginData;
    private Login user;

    /**
     * Setup method before each test.
     * Initializes the test data.
     */
    @BeforeEach
    void setUp() {
        loginData = new loginDTO();
        loginData.setUserName("testUser");
        loginData.setPassword("testPassword");

        user = new Login();
        user.setUserName("testUser");
        user.setPassword("testPassword");
        user.setUserId(UUID.randomUUID());
    }

    /**
     * Clean up after each test.
     * Resets mocks to ensure fresh state between tests.
     */
    @AfterEach
    void tearDown() {
        reset(loginDao);
    }

    /**
     * Test case for validating successful login.
     * Mocks the DAO to return the user for the valid credentials.
     */
    @Test
    void validateLogin_success() {
        when(loginDao.findById(loginData.getUserName())).thenReturn(Optional.of(user));

        UUID result = loginServices.validateLogin(loginData);

        assertNotNull(result);
        assertEquals(user.getUserId(), result);
        verify(loginDao, times(1)).findById(loginData.getUserName());
    }

    /**
     * Test case for validating login with an invalid password.
     * Mocks the DAO to return a user but changes the password to an incorrect value.
     */
    @Test
    void validateLogin_invalidPassword() {
        user.setPassword("wrongPassword");
        when(loginDao.findById(loginData.getUserName())).thenReturn(Optional.of(user));

        UUID result = loginServices.validateLogin(loginData);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result);  // Expect default UUID
        verify(loginDao, times(1)).findById(loginData.getUserName());
    }

    /**
     * Test case for validating login when the user is not found.
     * Mocks the DAO to return an empty Optional to simulate user not found.
     */
    @Test
    void validateLogin_userNotFound() {
        when(loginDao.findById(loginData.getUserName())).thenReturn(Optional.empty());

        UUID result = loginServices.validateLogin(loginData);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result);  // Expect default UUID
        verify(loginDao, times(1)).findById(loginData.getUserName());
    }

    /**
     * Test case for validating login with an empty username.
     * Mocks the input to have a null username and checks if default UUID is returned.
     */
    @Test
    void validateLogin_emptyUsername() {
        loginData.setUserName(null);

        UUID result = loginServices.validateLogin(loginData);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result);  // Expect default UUID
    }

    /**
     * Test case for validating login with an empty password.
     * Mocks the input to have a null password and checks if default UUID is returned.
     */
    @Test
    void validateLogin_emptyPassword() {
        loginData.setPassword(null);

        UUID result = loginServices.validateLogin(loginData);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result);  // Expect default UUID
    }

    /**
     * Test case for handling exceptions during login validation.
     * Simulates a database error by throwing a RuntimeException.
     */
    @Test
    void validateLogin_exceptionHandling() {
        when(loginDao.findById(anyString())).thenThrow(new RuntimeException("Database error"));

        UUID result = loginServices.validateLogin(loginData);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), result);  // Expect default UUID
    }
}