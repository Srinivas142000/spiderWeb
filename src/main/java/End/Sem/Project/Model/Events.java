package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "events")  // Table names are typically in lowercase
public class Events {
    @Id
    private UUID eventNum;
    private String eventName;  // Assuming this is a unique identifier
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String eventLocation;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private boolean eventRegistration;
}
