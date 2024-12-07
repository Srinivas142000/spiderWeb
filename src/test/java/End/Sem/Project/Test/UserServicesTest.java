package End.Sem.Project.Test;

import End.Sem.Project.DTO.UserDTO;
import End.Sem.Project.DTO.loginDTO;
import End.Sem.Project.Dao.*;
import End.Sem.Project.Model.Users;
import End.Sem.Project.Services.CommunityServices;
import End.Sem.Project.Services.UserServices;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServicesTest {
    @Mock
    private UserDao ud = Mockito.mock(UserDao.class);

    @Mock
    private CommunityDao cdao = Mockito.mock(CommunityDao.class);

    @Mock
    private LoginDao ldao = Mockito.mock(LoginDao.class);

    @Mock
    private UserMappingDao umd = Mockito.mock(UserMappingDao.class);

    @Mock
    private CommunityServices cms = Mockito.mock(CommunityServices.class);

    @Mock
    private UserCommunitiesDao ucd = Mockito.mock(UserCommunitiesDao.class);

    @Mock
    private CommunityDao cdc = Mockito.mock(CommunityDao.class);

    @InjectMocks
    private UserServices us;

    private UUID userId;
    private Users user;
    private loginDTO login;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        us = new UserServices(ud, umd, cdao, ldao, ucd, cms, cdc);
        userDTO = new UserDTO();
        userId = UUID.randomUUID();
        user = new Users();
        login = new loginDTO();
        login.setUserName("testUser");
        login.setPassword("testPassword");
        login.setFirstName("Test");
        login.setLastName("User");
        login.setEmail("test@test.com");
        login.setGender("NA");
        login.setDno(90);
        login.setStreetAddress("test address");
        login.setCity("test city");
        login.setState("test state");
        login.setCountry("test country");
        login.setZip("test zip");
        login.setPhone("324-573-3643");
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(ud, umd, cdao, ldao, cms);
    }
    @Test
    void registerNewUser_success() {
        // Mock the necessary behavior
        Mockito.when(ud.save(Mockito.any(Users.class))).thenReturn(user);
        Mockito.when(cms.ensureGeneralCommunityExists()).thenReturn(UUID.randomUUID());
        boolean result = us.registerNewUser(login);
        assertTrue(result);
    }


    @Test
    void registerNewUser_failure_missingField() {
        login.setUserName(null);  // Simulate missing username
        boolean result = us.registerNewUser(login);
        assertFalse(result);
    }

    @Test
    void registerUserToNewCommunity_success() {
        userDTO.setUserId(userId);
        userDTO.setCommunityId(UUID.randomUUID());
        userDTO.setCommunityName("Test Community");

        Mockito.when(umd.save(Mockito.any())).thenReturn(null);
        boolean result = us.registerUserToNewCommunity(userDTO);
        assertTrue(result);
    }

    @Test
    void registerUserToNewCommunity_failure_missingFields() {
        userDTO.setUserId(userId);
        userDTO.setCommunityId(null);

        boolean result = us.registerUserToNewCommunity(userDTO);
        assertFalse(result);
    }

    @Test
    void registerUserToNewCommunity_failure_nullUserId() {
        userDTO.setUserId(null);
        userDTO.setCommunityId(UUID.randomUUID());
        userDTO.setCommunityName("Test Community");

        boolean result = us.registerUserToNewCommunity(userDTO);
        assertFalse(result);
    }
}
