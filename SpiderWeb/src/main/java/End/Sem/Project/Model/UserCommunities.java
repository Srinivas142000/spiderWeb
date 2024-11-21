package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usercommunities")
public class UserCommunities {
    @Id
    private UUID communityId;
    private String communityName;
    private String communityDescription;
    private int userMaxCount;
    private int userCurrCount;

    @ManyToMany
    @JoinTable(
            name = "UserMapping",
            joinColumns = @JoinColumn(name = "communityId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<Users> users;



    @Setter
    @Transient
    private List<String> usersList;

    public List<String> getCombinedUserNames() {
        return users.stream()
                .map(user -> user.getFirstName() + " " + user.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "UserCommunities{" +
                "communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", communityDescription='" + communityDescription + '\'' +
                ", userMaxCount=" + userMaxCount +
                ", userCurrCount=" + userCurrCount +
                ", usersList=" + (usersList != null ? usersList : "[]") +
                '}';
    }

    public void setCombinedUserNames(List<String> usersList) {
        this.usersList = usersList;
    }
}
