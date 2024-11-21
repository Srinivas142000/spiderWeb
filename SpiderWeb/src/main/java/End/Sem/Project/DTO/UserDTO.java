package End.Sem.Project.DTO;

import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class UserDTO {
    private UUID communityId;
    private UUID userId;
    private String communityName;
}