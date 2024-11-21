package End.Sem.Project.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import End.Sem.Project.Model.*;

public interface LoginDao extends JpaRepository <Login, String> {
}
