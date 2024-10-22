package End.Sem.Project.Controllers;

import End.Sem.Project.DTO.CreateEventDTO;
import End.Sem.Project.Model.Events;
import End.Sem.Project.Services.EventServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import java.util.List;


@RestController
@RequestMapping("/event")
public class EventController {
    Events E = new Events();

    @Autowired
    EventServices eventServices;

    @GetMapping("/{eventName}")
    public JSONObject getEventInfo(@PathVariable String eventName){
        return eventServices.getSpecificEvent(eventName);
    }

    @GetMapping("/all")
    public List<Events> getAllEventsInfo(){
        return eventServices.getAllEvents();
    }

    @PatchMapping("/modifyEvent")
    public boolean modifyEvent(@RequestBody CreateEventDTO createEventDTO){
        return eventServices.modifyEvent(createEventDTO);
    }

    @DeleteMapping("/removeEvent")
    public boolean removeEvent(@RequestBody CreateEventDTO createEventDTO){
        return eventServices.deleteEvent(createEventDTO);
    }

    @PostMapping("/")
    public boolean addEvent(@RequestBody CreateEventDTO eventDetails){
        return eventServices.addEvent(eventDetails);
    }
}
