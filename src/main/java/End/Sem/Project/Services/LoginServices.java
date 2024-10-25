package End.Sem.Project.Services;

import End.Sem.Project.DTO.loginDTO;
import End.Sem.Project.Dao.CommunityDao;
import End.Sem.Project.Dao.LoginDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Dao.UserMappingDao;
import End.Sem.Project.Helpers.CommonHelpers;
import End.Sem.Project.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class for login-related operations, including validating logins.
 */
@Service
public class LoginServices extends CommonHelpers {

    private final LoginDao loginDao;
    private final UserDao userDao;
    private final UserMappingDao umDao;
    private final CommunityServices cmServices;

    /**
     * DAO and classes instantiation using Constructor
     *
     * @param loginDao    DAO for login operations.
     * @param userDao     DAO for user operations.
     * @param umDao       DAO for user mapping operations.
     * @param cmServices  Multiple Services for community-related operations.
     */
    @Autowired
    public LoginServices(LoginDao loginDao, UserDao userDao, UserMappingDao umDao, CommunityServices cmServices) {
        this.loginDao = loginDao;
        this.userDao = userDao;
        this.umDao = umDao;
        this.cmServices = cmServices;
    }

    /**
     * Validates the login credentials of a user based on the provided login DTO.
     *
     * @param lg the loginDTO containing the username and password for validation.
     * @return true if login valid, or else false.
     */
    public boolean validateLogin(loginDTO lg) {
        try {
            if (isNullString(lg.getUserName()) || isNullString(lg.getPassword())) {
                throw new Exception("Mandatory Field is Empty");
            }
            Login user = loginDao.getById(lg.getUserName());
            if (user.getUserName().equals(lg.getUserName()) && user.getPassword().equals(lg.getPassword())) {
                return true;
            }
            System.out.println("Details Mismatch !");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
