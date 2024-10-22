package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "User")
public class User {
    @Id
    private UUID userId;
    private String userName;
    private String password;
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
