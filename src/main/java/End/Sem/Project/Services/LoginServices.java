package End.Sem.Project.Services;

import End.Sem.Project.DTO.loginDTO;
import End.Sem.Project.Dao.LoginDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Dao.UserMappingDao;
import End.Sem.Project.Helpers.CommonHelpers;
import End.Sem.Project.Model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class responsible for login-related operations, such as validating user login credentials.
 * It includes methods for checking the correctness of username and password and returning the corresponding user UUID.
 */
@Service
public class LoginServices extends CommonHelpers {

    private final LoginDao loginDao;
    private final UserDao userDao;
    private final UserMappingDao umDao;
    private final CommunityServices cmServices;

    /**
     * Constructs a LoginServices instance with the required DAOs and services.
     * This constructor injects the necessary dependencies for performing login validation and related operations.
     *
     * @param loginDao    DAO for interacting with login-related data.
     * @param userDao     DAO for interacting with user-related data.
     * @param umDao       DAO for interacting with user mapping data.
     * @param cmServices  Service for managing community-related operations.
     */
    @Autowired
    public LoginServices(LoginDao loginDao, UserDao userDao, UserMappingDao umDao, CommunityServices cmServices) {
        this.loginDao = loginDao;
        this.userDao = userDao;
        this.umDao = umDao;
        this.cmServices = cmServices;
    }

    /**
     * Validates the provided login credentials (username and password) against stored user data.
     *
     * This method checks if the provided username and password match an existing user in the database.
     * If the login is successful, it returns the user’s UUID. If there is an error (e.g., invalid credentials, user not found),
     * it returns a default UUID to indicate failure.
     *
     * @param lg the loginDTO containing the username and password to validate.
     * @return the UUID of the user if the login is successful; otherwise, a default UUID (00000000-0000-0000-0000-000000000000).
     *         The default UUID is returned in cases of invalid credentials, user not found, or any errors during the validation.
     *
     * @throws Exception if a required field (username or password) is empty or if another error occurs during validation.
     */
    public UUID validateLogin(loginDTO lg) {
        try {
            if (isNullString(lg.getUserName()) || isNullString(lg.getPassword())) {
                throw new Exception("Mandatory Field is Empty");
            }

            String userName = lg.getUserName();
            Optional<Login> user = loginDao.findById(userName);

            if (user.isPresent()) {
                Login l = user.get();
                if (l.getUserName().equalsIgnoreCase(lg.getUserName()) && l.getPassword().equalsIgnoreCase(lg.getPassword())) {
                    return l.getUserId();
                }
                System.out.println("Details Mismatch!");
            } else {
                System.out.println("User not found");
            }
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
