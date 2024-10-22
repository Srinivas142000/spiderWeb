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
@Table(name = "event_info")  // Table names are typically in lowercase
public class Event_Info {  // Changed to camel case
    @Id
    private UUID eventNum;  // Changed to camelCase
    private String eventDescription;  // Changed to camelCase
    private String boardPosition;  // Changed to camelCase
    private int eventAvailability;  // Changed to camelCase
}
