package End.Sem.Project.DTO;

import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class CommunityDTO {
    private UUID communityId;
    private String communityName;
    private String communityDescription;
    private int userMaxCount;
    private int userCurrCount;
    private List<String> communityMembers;
}