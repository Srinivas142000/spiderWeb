package End.Sem.Project.Services;

import End.Sem.Project.DTO.loginDTO;
import End.Sem.Project.Dao.CommunityDao;
import End.Sem.Project.Dao.LoginDao;
import End.Sem.Project.Dao.UserDao;
import End.Sem.Project.Dao.UserMappingDao;
import End.Sem.Project.Helpers.CommonHelpers;
import End.Sem.Project.Model.*;
import java.util.List;
import java.util.ArrayList;

import java.util.Optional;
import java.util.UUID;

public class LoginServices extends CommonHelpers {

    private final LoginDao loginDao;
    private final UserDao userDao;
    private final UserMappingDao umDao;

    public LoginServices(LoginDao loginDao, UserDao userDao, UserMappingDao umDao) {
        this.loginDao = loginDao;
        this.userDao = userDao;
        this.umDao = umDao;
    }

    public boolean validateLogin(loginDTO lg) {
        try {
            // Corrected null check logic
            if (isNullString(lg.getUserName()) || isNullString(lg.getPassword())) {
                throw new Exception("Mandatory Field is Empty");
            }

            // Retrieve user by username
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

        User newUser = new User();
        UserMapping um = new UserMapping();
        Login lgn = new Login();

        UUID newUuid = UUID.randomUUID();
        lgn.setUserName(lg.getUserName());
        lgn.setPassword(lg.getPassword());
        lgn.setUserId(newUuid);
        loginDao.save(lgn);

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
        userDao.save(newUser);

//        List<UUID> cgUUids = new ArrayList<>();
//        Optional<CommunityDao> general = CommunityDao.findByName("General");
//
//        if (general.isPresent()) {
//            UUID generalUuid = general.get().getUuid(); // Assuming there's a method to get the UUID
//            cgUUids.add(generalUuid);
//        } else {
//            // Handle the case where the community is not found
//            System.out.println("Community 'General' not found.");
//        }


        return false;
    }
}
