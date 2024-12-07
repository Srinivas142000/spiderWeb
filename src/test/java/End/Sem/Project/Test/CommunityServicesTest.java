package End.Sem.Project.Test;

import End.Sem.Project.Dao.CommunityDao;
import End.Sem.Project.Model.UserCommunities;
import End.Sem.Project.Services.CommunityServices;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CommunityServicesTest {

    @Mock
    private CommunityDao coDao;

    @InjectMocks
    private CommunityServices communityServices;

    private UUID communityId;
    private UserCommunities existingCommunity;

    @BeforeEach
    void setUp() {
        communityId = UUID.randomUUID();
        existingCommunity = new UserCommunities();
        existingCommunity.setCommunityId(communityId);
        existingCommunity.setCommunityName("Test");
        existingCommunity.setCommunityDescription("Test Community");
        existingCommunity.setUserCurrCount(0);
        existingCommunity.setUserMaxCount(1000);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(coDao);
    }

    @Test
    void ensureGeneralCommunityExists_whenCommunityExists() {
        Mockito.when(coDao.findByCommunityNameIgnoreCase("general"))
                .thenReturn(Optional.of(existingCommunity));

        UUID genId = communityServices.ensureGeneralCommunityExists();

        Assertions.assertEquals(existingCommunity.getCommunityId(), genId);
        Mockito.verify(coDao).findByCommunityNameIgnoreCase("general");
        Mockito.verify(coDao, Mockito.never()).save(Mockito.any(UserCommunities.class));
    }


    // Making sure we create a mock general community when we can't find the real thing
    @Test
    void ensureGeneralCommunityExists_whenCommunityDoesNotExist() {
        Mockito.when(coDao.findByCommunityNameIgnoreCase("general"))
                .thenReturn(Optional.empty());

        UUID genId = communityServices.ensureGeneralCommunityExists();

        Assertions.assertNotNull(genId);
        Mockito.verify(coDao).save(Mockito.any(UserCommunities.class));
    }


    @Test
    void getDetails_whenCommunityExists() {
        Mockito.when(coDao.findByCommunityNameIgnoreCase("Test")).thenReturn(Optional.of(existingCommunity));
        Optional<UserCommunities> uc = communityServices.getDetails("Test");
        Assertions.assertTrue(uc.isPresent());
        Assertions.assertEquals(uc.get().getCommunityId(), communityId);
        Assertions.assertEquals(uc.get().getCommunityName(), existingCommunity.getCommunityName());
    }

    @Test
    void incrementGeneralCommunityCount() {
        existingCommunity.setUserCurrCount(5);
        Mockito.when(coDao.findByCommunityNameIgnoreCase("General"))
                .thenReturn(Optional.of(existingCommunity));
        communityServices.incrementCommunityCount("General");

        Assertions.assertEquals(6, existingCommunity.getUserCurrCount());
        Mockito.verify(coDao).save(existingCommunity);
    }

    @Test
    void allCommunities() {
        UserCommunities community1 = new UserCommunities();
        community1.setCommunityId(UUID.randomUUID());
        community1.setCommunityName("Community 1");

        UserCommunities community2 = new UserCommunities();
        community2.setCommunityId(UUID.randomUUID());
        community2.setCommunityName("Community 2");

        Mockito.when(coDao.findAll()).thenReturn(List.of(community1, community2));

        var communities = communityServices.allCommunities();

        Assertions.assertEquals(2, communities.size());
//        Assertions.assertEquals("Community 1", communities.get(0).getCommunityName());
//        Assertions.assertEquals("Community 2", communities.get(1).getCommunityName());
    }
}
