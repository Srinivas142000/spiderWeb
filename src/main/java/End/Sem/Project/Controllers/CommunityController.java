package End.Sem.Project.Controllers;


import End.Sem.Project.Model.UserCommunities;
import org.springframework.web.bind.annotation.*;
import End.Sem.Project.Services.CommunityServices;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    CommunityServices communityServices;

    @GetMapping("/getDetails/{communityName}")
    private Optional<UserCommunities> getDetails(@PathVariable String communityName) {
        return communityServices.getDetails(communityName);
    }

    @GetMapping("/all")
    private List<UserCommunities> getAll() {
        return communityServices.allCommunities();
    }
}
