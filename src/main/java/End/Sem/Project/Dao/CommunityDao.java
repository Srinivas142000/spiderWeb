package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import End.Sem.Project.Model.UserCommunities;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.Optional;

public interface CommunityDao extends JpaRepository<UserCommunities, UUID> {
    @Query("SELECT uc FROM UserCommunities uc WHERE LOWER(uc.communityName) = LOWER(:communityName)")
    Optional<UserCommunities> findByCommunityNameIgnoreCase(@Param("communityName") String communityName);
}
