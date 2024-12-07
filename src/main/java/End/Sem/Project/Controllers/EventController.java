package End.Sem.Project.Controllers;

import End.Sem.Project.DTO.CreateEventDTO;
import End.Sem.Project.Model.Events;
import End.Sem.Project.Services.EventServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Response;

/**
 * Controller for handling event-related requests.
 */
@RestController
@RequestMapping("/event")
public class   EventController {
    Events E = new Events();

    @Autowired
    EventServices eventServices;

    /**
     * Fetches information about a specific event.
     *
     * @param eventId the name of the event to retrieve
     * @return a JSONObject containing event details
     */
    @GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEventInfo(@PathVariable UUID eventId) {
        JSONObject obj = eventServices.getSpecificEvent(eventId);;
        return obj.toString();
    }

    /**
     * Fetches information about all events.
     * @return a list of all events
     */
    @GetMapping("/all")
    public List<Events> getAllEventsInfo() {
        return eventServices.getAllEvents();
    }

    /**
     * Modifies an existing event with the provided details.
     * @param createEventDTO the data transfer object containing event details to modify
     * @return true if the modification was successful, false otherwise
     */
    @PatchMapping("/modifyEvent")
    public boolean modifyEvent(@RequestBody CreateEventDTO createEventDTO) {
        return eventServices.modifyEvent(createEventDTO);
    }

    /**
     * Deletes an event based on the provided details.
     * @param createEventDTO the data transfer object containing event details to delete
     * @return true if the deletion was successful, false otherwise
     */
    @DeleteMapping("/removeEvent")
    public boolean removeEvent(@RequestBody CreateEventDTO createEventDTO) {
        return eventServices.deleteEvent(createEventDTO);
    }

    /**
     * Adds a new event with the provided details.
     * @param eventDetails the data transfer object containing details of the event to add
     * @return true if the addition was successful, false otherwise
     */
    @PostMapping("/")
    public boolean addEvent(@RequestBody CreateEventDTO eventDetails) {
        return eventServices.addEvent(eventDetails);
    }
}
