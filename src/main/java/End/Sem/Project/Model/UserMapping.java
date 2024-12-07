package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents the mapping between users and communities in the system.
 * This class holds the user ID and community ID, establishing
 * a many-to-many relationship between users and communities.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usermapping")
public class UserMapping {
    @Id
    private UUID userId;

    private UUID communityId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "communityId", referencedColumnName = "communityId", insertable = false, updatable = false)
    private UserCommunities community;
}