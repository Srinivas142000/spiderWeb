package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface UserCommunitiesDao extends JpaRepository <UserMapping, UUID> {

}
