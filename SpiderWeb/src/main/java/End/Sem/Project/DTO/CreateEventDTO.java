package End.Sem.Project.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateEventDTO {
    private String eventLocation;
    private String eventName;
    private boolean eventRegistration;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private int eventAvailability;
    private String eventDescription;
    private String boardPosition;
    private UUID eventNum;
    private String uuid;
}
