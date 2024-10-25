package End.Sem.Project.Controllers;

import org.springframework.web.bind.annotation.*;
import End.Sem.Project.Services.*;
import End.Sem.Project.DTO.*;

/**
 * Controller for managing user login and registration operations.
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private final CommunityServices communityServices;
    private final UserServices userServices;
    private final LoginServices loginServices;

    /**
     * Constructs a new LoginController with the specified services.
     *
     * @param communityServices the CommunityServices instance
     * @param userServices      the UserServices instance
     */
    public LoginController(CommunityServices communityServices, UserServices userServices, LoginServices loginServices) {
        this.communityServices = communityServices;
        this.userServices = userServices;
        this.loginServices = loginServices;
    }

    /**
     * Validates user login credentials.
     * @param lg the loginDTO containing username and password
     * @return true if the login is successful, false otherwise
     */
    @GetMapping("/validate")
    private boolean login(@RequestBody loginDTO lg) {
        return loginServices.validateLogin(lg);
    }

    /**
     * Registers a new user with the provided credentials.
     * @param lg the loginDTO containing user registration details
     * @return true if registration is successful, false otherwise
     */
    @PostMapping("/register")
    private boolean register(@RequestBody loginDTO lg) {
        return userServices.registerNewUser(lg);
    }
}
