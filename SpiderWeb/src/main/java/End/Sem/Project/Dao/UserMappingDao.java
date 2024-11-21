package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;
import java.util.UUID;

public interface UserMappingDao extends JpaRepository <UserMapping, UUID> {
}