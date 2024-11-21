package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import End.Sem.Project.Model.*;

public interface EventsDao extends JpaRepository <Events, UUID> {
}
