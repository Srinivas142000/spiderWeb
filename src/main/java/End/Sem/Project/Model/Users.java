package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.List;

/**
 * Represents a user in the system.
 * This class holds user information including their unique identifier,
 * name, contact details, and address.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    private UUID userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private int dno;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String country;

    @ManyToMany(mappedBy = "users")
    private List<UserCommunities> communities;
}