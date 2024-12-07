package End.Sem.Project.Controllers;

import End.Sem.Project.DTO.UserDTO;
import End.Sem.Project.Model.UserCommunities;
import End.Sem.Project.Services.UserServices;
import End.Sem.Project.Services.CommunityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * Controller for community-related operations.
 */
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityServices communityServices;

    private UserServices userServices;

    /**
     * Fetches the details of a specific community by its name.
     *
     * @param communityName the name of the community to fetch
     * @return community details
     */
    @GetMapping("/getDetails/{communityName}")
    public Optional<UserCommunities> getDetails(@PathVariable String communityName) {
        Optional<UserCommunities> communityOpt = communityServices.getDetails(communityName);
        return communityOpt;
    }

    /**
     * Registers a user to a specified community.
     *
     * @param userMapping the UserDTO containing user information for registration
     * @return true if the registration was successful, false otherwise
     */
    @PostMapping("addToCommunity")
    public boolean registerToCommunity(@RequestBody UserDTO userMapping) {
        return userServices.registerUserToNewCommunity(userMapping);
    }

    /**
     * Fetches a list of all communities.
     *
     * @return UserCommunities
     */
    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {
        // Fetch the list of JSON-like objects from the service
        return communityServices.allCommunities();
    }
}
