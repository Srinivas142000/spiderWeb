package End.Sem.Project.Test;

import End.Sem.Project.Dao.LoginDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Dao.UserMappingDao;
import End.Sem.Project.Model.Login;
import End.Sem.Project.Services.CommunityServices;
import End.Sem.Project.Services.LoginServices;
import End.Sem.Project.DTO.loginDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServicesTest {

    @Mock
    private LoginDao loginDao;

    @Mock
    private UserDao userDao;

    @Mock
    private UserMappingDao umDao;

    @InjectMocks
    private CommunityServices cmServices;

    @InjectMocks
    private LoginServices loginServices;

    private loginDTO loginData;
    private Login user;

    @BeforeEach
    void setUp() {
        loginDao = mock(LoginDao.class);
        loginServices = new LoginServices(loginDao,userDao,umDao, cmServices);
        loginData = new loginDTO();
        loginData.setUserName("testUser");
        loginData.setPassword("testPassword");

        user = new Login();
        user.setUserName("testUser");
        user.setPassword("testPassword");
    }

    @AfterEach
    void tearDown() {
        reset(loginDao);
    }

    @Test
    void validateLogin_success() {
        when(loginDao.getById(loginData.getUserName())).thenReturn(user);

        boolean result = loginServices.validateLogin(loginData);

        assertTrue(result);
    }

    @Test
    void validateLogin_invalidPassword() {
        user.setPassword("wrongPassword");
        when(loginDao.getById(loginData.getUserName())).thenReturn(user);

        boolean result = loginServices.validateLogin(loginData);

        assertFalse(result);
        verify(loginDao, times(1)).getById(loginData.getUserName());
    }

    @Test
    void validateLogin_userNotFound() {
        when(loginDao.getById(loginData.getUserName())).thenReturn(null);

        boolean result = loginServices.validateLogin(loginData);

        assertFalse(result);
        verify(loginDao, times(1)).getById(loginData.getUserName());
    }

    @Test
    void validateLogin_emptyUsername() {
        loginData.setUserName(null);
        assertFalse(loginServices.validateLogin(loginData));
    }

    @Test
    void validateLogin_emptyPassword() {
        loginData.setPassword(null);
        assertFalse(loginServices.validateLogin(loginData));
    }

    @Test
    void validateLogin_exceptionHandling() {
        when(loginDao.getById(anyString())).thenThrow(new RuntimeException("Database error"));

        boolean result = loginServices.validateLogin(loginData);

        assertFalse(result);
    }
}
