package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserMappingDao extends JpaRepository <UserMapping, UUID> {
    @Query("SELECT um.communityId FROM UserMapping um WHERE um.userId = :userId")
    List<UUID> findCommunityIdsByUserId(@Param("userId") UUID userId);
}