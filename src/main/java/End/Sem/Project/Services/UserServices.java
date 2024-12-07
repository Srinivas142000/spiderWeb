package End.Sem.Project.Services;

import End.Sem.Project.DTO.loginDTO;
import End.Sem.Project.DTO.UserDTO;
import End.Sem.Project.Dao.CommunityDao;
import End.Sem.Project.Dao.UserCommunitiesDao;
import End.Sem.Project.Dao.LoginDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Dao.UserMappingDao;
import End.Sem.Project.Helpers.CommonHelpers;
import End.Sem.Project.Model.Login;
import End.Sem.Project.Model.UserCommunities;
import End.Sem.Project.Model.UserMapping;
import End.Sem.Project.Model.Users;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A Service class which handles user-related operations such as registration and community registration.
 */
@Service
public class UserServices extends CommonHelpers {
    private final UserDao udao;
    private final UserMappingDao umdao;
    private final CommunityDao cmdao;
    private final LoginDao ldao;
    private final CommunityServices cmServices;
    private final UserCommunitiesDao ucdao;
    private final CommunityDao cdao;

    /**
     * Instantiates all Dao's and other needed service methods
     *
     * @param udao          User DAO.
     * @param umdao         User mapping DAO.
     * @param cmdao         Community DAO.
     * @param ldao          Login DAO.
     * @param cmServices    Community services class instantiation.
     */
    @Autowired
    public UserServices(UserDao udao, UserMappingDao umdao, CommunityDao cmdao, LoginDao ldao, UserCommunitiesDao ucdao, CommunityServices cmServices, CommunityDao cdao) {
        this.udao = udao;
        this.umdao = umdao;
        this.cmdao = cmdao;
        this.ldao = ldao;
        this.cmServices = cmServices;
        this.ucdao = ucdao;
        this.cdao = cdao;
    }

    /**
     * Registers a new user with the provided login details.
     * Adds the user mapping community as well.
     *
     * @param lg the loginDTO containing all the user registration information.
     * @return true if the registration is successful, false if any required field is missing or invalid or registration fails.
     */
    public boolean registerNewUser(loginDTO lg) {
        if (isNullString(lg.getUserName()) ||
                isNullString(lg.getPassword()) ||
                isNullString(lg.getFirstName()) ||
                isNullString(lg.getLastName()) ||
                isNullString(lg.getEmail()) ||
                isNullString(lg.getPhone()) ||
                isNullString(lg.getGender()) ||
                lg.getDno() <= 0 ||
                isNullString(lg.getStreetAddress()) ||
                isNullString(lg.getCity()) ||
                isNullString(lg.getState()) ||
                isNullString(lg.getZip()) ||
                isNullString(lg.getCountry()) ||
                isNullString(lg.getPassword())) {
            return false;
        }

        Users newUser = new Users();
        UserMapping um = new UserMapping();
        Login lgn = new Login();

        UUID newUuid = UUID.randomUUID();
        lgn.setUserName(lg.getUserName());
        lgn.setPassword(lg.getPassword());
        lgn.setUserId(newUuid);
        ldao.save(lgn);

        newUser.setUserName(lg.getUserName());
        newUser.setFirstName(lg.getFirstName());
        newUser.setLastName(lg.getLastName());
        newUser.setEmail(lg.getEmail());
        newUser.setPhone(lg.getPhone());
        newUser.setGender(lg.getGender());
        newUser.setDno(lg.getDno());
        newUser.setStreetAddress(lg.getStreetAddress());
        newUser.setCity(lg.getCity());
        newUser.setState(lg.getState());
        newUser.setZip(lg.getZip());
        newUser.setCountry(lg.getCountry());
        newUser.setUserId(newUuid);
        udao.save(newUser);

        if(cmServices != null) {
            UUID generalCommunityId = cmServices.ensureGeneralCommunityExists();
            UserMapping userMapping = new UserMapping();
            userMapping.setUserId(newUuid);
            userMapping.setCommunityId(generalCommunityId);
            umdao.save(userMapping);
            cmServices.incrementCommunityCount("General");
        }

        return true;
    }

    /**
     * Registers an existing user to a specified community and increases the member count.
     *
     * @param ucdto the UserDTO containing user ID, community ID & communityName.
     * @return true if the user is successfully registered to the community, false if user ID or community ID is null.
     */
    public boolean registerUserToNewCommunity(UserDTO ucdto) {
        if (ucdto.getUserId() != null && ucdto.getCommunityId() != null && ucdto.getCommunityName() != null) {
            UserMapping mapping = new UserMapping();
            mapping.setUserId(ucdto.getUserId());
            mapping.setCommunityId(ucdto.getCommunityId());
            umdao.save(mapping);
            cmServices.incrementCommunityCount(ucdto.getCommunityName());
            return true;
        } else {
            return false;
        }
    }

    public JSONObject userDetails(UUID userId) {
        JSONObject user = new JSONObject();
        Optional<Users> u = udao.findById(userId);
        if (u.isPresent()) {
            Users userEntity = u.get();
            user.put("userId", userEntity.getUserId());
            user.put("userName", userEntity.getUserName());
            user.put("firstName", userEntity.getFirstName());
            user.put("lastName", userEntity.getLastName());
            user.put("email", userEntity.getEmail());
            user.put("phone", userEntity.getPhone());
            user.put("gender", userEntity.getGender());
            user.put("dno", userEntity.getDno());
            user.put("streetAddress", userEntity.getStreetAddress());
            user.put("city", userEntity.getCity());
            user.put("state", userEntity.getState());
            user.put("zip", userEntity.getZip());
            user.put("country", userEntity.getCountry());
        }

        // Fetch community IDs for the user
        List<UUID> result = umdao.findCommunityIdsByUserId(userId);

        JSONArray communitiesArray = new JSONArray();
        for (UUID communityId : result) {
            Optional<UserCommunities> communityOpt = cdao.findById(communityId);

            if (communityOpt.isPresent()) {
                UserCommunities community = communityOpt.get();

                // Create a JSONObject for the community and populate its details
                JSONObject communityObj = new JSONObject();
                communityObj.put("communityId", community.getCommunityId());
                communityObj.put("communityName", community.getCommunityName());
                communityObj.put("communityDescription", community.getCommunityDescription());
                communityObj.put("userMaxCount", community.getUserMaxCount());
                communityObj.put("userCurrCount", community.getUserCurrCount());

                // Add the community JSONObject to the communities array
                communitiesArray.put(communityObj);
            }
        }

        // Add the communities array to the user JSONObject
        user.put("communities", communitiesArray);

        return user;
    }
}
