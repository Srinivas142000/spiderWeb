package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface UserDao extends JpaRepository <Users, UUID> {
    @Query("SELECT CONCAT(u.firstName, ' ', u.lastName) FROM Users u WHERE u.userId IN :userNums")
    List<String> findFullNamesByUserNums(List<Users> userNums);
}
