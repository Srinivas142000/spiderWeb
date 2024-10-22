package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface UserDao extends JpaRepository <User, UUID> {
    @Query("SELECT CONCAT(u.firstName, ' ', u.lastName) FROM User u WHERE u.userId IN :userNums")
    List<String> findFullNamesByUserNums(List<User> userNums);
}
