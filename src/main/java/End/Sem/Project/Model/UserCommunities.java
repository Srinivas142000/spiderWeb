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
@Table(name = "UserCommunities")
public class UserCommunities {
    @Id
    private UUID communityId;
    private String communityName;
    private String communityDescription;
    private int userMaxCount;
    private int userCurrCount;

    @ManyToMany
    @JoinTable(
            name = "UserCommunityMapping",
            joinColumns = @JoinColumn(name = "communityId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> users; // List of users in this community
}
