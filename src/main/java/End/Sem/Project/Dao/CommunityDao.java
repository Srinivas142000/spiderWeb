package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;

import java.util.UUID;
import java.util.Optional;

public interface CommunityDao extends JpaRepository <UserCommunities, UUID> {
    Optional<UserCommunities> findByName(String name);
}