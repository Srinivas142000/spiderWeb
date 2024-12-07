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
@Table(name = "eventinfo")
public class EventInfo {
    @Id
    private UUID eventNum;
    private String eventDescription;
    private String boardPosition;
    private int eventAvailability;
}
