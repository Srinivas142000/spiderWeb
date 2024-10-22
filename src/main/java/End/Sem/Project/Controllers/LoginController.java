package End.Sem.Project.Controllers;


import org.springframework.web.bind.annotation.*;
import End.Sem.Project.Services.*;
import End.Sem.Project.DTO.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    LoginServices loginServices;

    @GetMapping("/validate")
    private boolean login(@RequestBody loginDTO lg) {
        return loginServices.validateLogin(lg);
    }

    @PostMapping("/register")
    private boolean register(@RequestBody loginDTO lg) {
        return loginServices.registerNewUser(lg);
    }
}
