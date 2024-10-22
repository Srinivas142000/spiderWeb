package End.Sem.Project.Services;

import End.Sem.Project.Dao.CommunityDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Model.UserCommunities;
import End.Sem.Project.Model.User;

import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommunityServices {
    CommunityDao commDao;
    UserDao userDao;
    public CommunityServices(CommunityDao commDao, UserDao userDao) {
        this.commDao = commDao;
        this.userDao = userDao;
    }

    public Optional<UserCommunities> getDetails(String commName) {
        Optional<UserCommunities> comm = null;
        UserCommunities userComm;
        try {
            comm = commDao.findByName(commName);
            if (comm.isPresent()) {
                userComm = comm.get();
                List<UUID> userUuids = userComm.getUsers().stream()
                        .map(User::getUserId)
                        .collect(Collectors.toList());
                List<String> communityUserList = userDao.findFullNamesByUserNums(userComm.getUsers());
            }
            return comm;
        } catch (Exception E) {
            E.printStackTrace();
        }
        return comm;
    }

    public List<UserCommunities> allCommunities() {
        return commDao.findAll();
    }
}
