package End.Sem.Project.Services;

import End.Sem.Project.DTO.CommunityDTO;
import End.Sem.Project.Dao.CommunityDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Model.UserCommunities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing community-related operations.
 */
@Service
public class CommunityServices {

    private final CommunityDao commDao;
    private final CommunityDao coDao;
    private final UserDao userDao;

    /**
     * DAO's and Classes Instantiation
     *
     * @param commDao DAO for community operations.
     * @param userDao DAO for user operations.
     * @param coDao   DAO for community operations.
     */
    @Autowired
    public CommunityServices(CommunityDao commDao, UserDao userDao, CommunityDao coDao) {
        this.commDao = commDao;
        this.userDao = userDao;
        this.coDao = coDao;
    }

    /**
     * Ensures that the general community exists in the database.
     * If it does not exist, creates a new general community.
     *
     * @return the UUID of the general community.
     */
    public UUID ensureGeneralCommunityExists() {
        Optional<UserCommunities> generalCommunityOpt = coDao.findByCommunityNameIgnoreCase("general");

        if (generalCommunityOpt.isPresent()) {
            return generalCommunityOpt.get().getCommunityId();
        }

        UserCommunities generalCommunity = new UserCommunities();
        generalCommunity.setCommunityId(UUID.randomUUID());
        generalCommunity.setCommunityName("General");
        generalCommunity.setCommunityDescription("Default community for all users");
        generalCommunity.setUserMaxCount(100);
        generalCommunity.setUserCurrCount(0);

        generalCommunity = coDao.save(generalCommunity);
        return generalCommunity.getCommunityId();
    }

    /**
     * Fetches the details of a community by its name.
     *
     * @param commName the name of the community to retrieve.
     * @return an Optional containing the UserCommunities if found, otherwise empty.
     */
    public Optional<UserCommunities> getDetails(String commName) {
        try {
            Optional<UserCommunities> temp = commDao.findByCommunityNameIgnoreCase(commName);

            if (temp.isPresent()) {
                UserCommunities community = temp.get();
                List<String> usersList = community.getUsers().stream()
                        .map(user -> user.getFirstName() + " " + user.getLastName())
                        .collect(Collectors.toList());
                community.setUsers(null);
                community.setUsersList(usersList);
            }

            return temp;

        } catch (Exception e) {
            Logger.getLogger(CommunityServices.class.getName()).log(Level.SEVERE, "Error fetching community details", e);
            return Optional.empty();
        }
    }

    /**
     * Increases the current user count for a specified community.
     *
     * @param commName the name of the community whose user count will be incremented.
     */
    public void incrementCommunityCount(String commName) {
        Optional<UserCommunities> generalCommunityOpt = commDao.findByCommunityNameIgnoreCase(commName);

        if (generalCommunityOpt.isPresent()) {
            UserCommunities generalCommunity = generalCommunityOpt.get();
            generalCommunity.setUserCurrCount(generalCommunity.getUserCurrCount() + 1);
            commDao.save(generalCommunity);
        }
    }

    /**
     * Gets a list of all communities.
     *
     * @return a list of UserCommunities.
     */
    public List<UserCommunities> allCommunities() {
        List<UserCommunities> uc = commDao.findAll();
        for (UserCommunities community : uc) {
            if (community.getUsers() != null) {
                List<String> usersList = community.getUsers().stream()
                        .map(user -> user.getFirstName() + " " + user.getLastName())
                        .collect(Collectors.toList());
            }
        }
        return uc;
    }
}
