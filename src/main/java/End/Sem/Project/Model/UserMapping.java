package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "UserMapping")
public class UserMapping {

    @Id
    private UUID userId;

    @Id
    private UUID communityId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "communityId", referencedColumnName = "communityId", insertable = false, updatable = false)
    private UserCommunities community; // Assuming UserCommunities is another entity

}
