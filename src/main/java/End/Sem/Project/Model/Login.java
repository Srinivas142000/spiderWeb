package End.Sem.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a login credential for a user in the system.
 * This class holds the user's username, password, and associated user ID.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "login")
public class Login {
    @Id
    private String userName;
    private String password;
    private UUID userId;


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public UUID getUserId() {
        return userId;
    }
}
