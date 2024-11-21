package End.Sem.Project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Represents an event in the system.
 * This class holds information about the event including its
 * unique identifier, name, dates, times, location, and registration status.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "events")
public class Events {
    @Id
    private UUID eventNum;
    private String eventName;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String eventLocation;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private boolean eventRegistration;
}