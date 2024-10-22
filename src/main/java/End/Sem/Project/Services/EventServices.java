package End.Sem.Project.Services;

import End.Sem.Project.DTO.CreateEventDTO;
import End.Sem.Project.Dao.EventInfoDao;
import End.Sem.Project.Dao.EventsDao;
import End.Sem.Project.Model.Event_Info;
import End.Sem.Project.Model.Events;
import End.Sem.Project.Helpers.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServices extends CommonHelpers {

    private final EventsDao eventsDao;
    private final EventInfoDao eventInfoDao;

    @Autowired
    public EventServices(EventsDao eventsDao, EventInfoDao eventInfoDao) {
        this.eventsDao = eventsDao;
        this.eventInfoDao = eventInfoDao;
    }

    public JSONObject getSpecificEvent(String name) {
        JSONObject rJSON = new JSONObject();
        // Logic to fetch specific event details and populate rJSON
        return rJSON;
    }

    public boolean modifyEvent(CreateEventDTO cdto) {
        try {
            if (cdto.getUuid() == null) {
                throw new IllegalArgumentException("Event UUID must not be null");
            }

            UUID u = UUID.fromString(cdto.getUuid());
            Optional<Events> existingEvent = eventsDao.findById(u);
            if (!existingEvent.isPresent()) {
                throw new Exception("Event not found for UUID: " + cdto.getUuid());
            }

            // Update existing event properties
            Events eventToUpdate = existingEvent.get();
            updateIfNotNullString(cdto.getEventName(), eventToUpdate::setEventName);
            updateIfNotNullString(cdto.getEventLocation(), eventToUpdate::setEventLocation);
            updateIfNotNullBool(cdto.isEventRegistration(), eventToUpdate::setEventRegistration);
            updateIfNotNullDate(cdto.getEventStartDate(), eventToUpdate::setEventStartDate);
            updateIfNotNullDate(cdto.getEventEndDate(), eventToUpdate::setEventEndDate);
            updateIfNotNullTime(cdto.getEventStartTime(), eventToUpdate::setEventStartTime);
            updateIfNotNullTime(cdto.getEventEndTime(), eventToUpdate::setEventEndTime);
            // Save the updated event
            eventsDao.save(eventToUpdate);
            System.out.println("Modified Event: " + eventToUpdate);

            // Update Event_Info
            Optional<Event_Info> eventInfoOptional = eventInfoDao.findById(u);
            if (!eventInfoOptional.isPresent()) {
                throw new Exception("Event info not found for UUID: " + cdto.getUuid());
            }

            Event_Info eventMod = eventInfoOptional.get();
            updateIfNotNullUUID(u, eventMod::setEventNum);
            updateIfNotNullInt(cdto.getEventAvailability(), eventMod::setEventAvailability);
            updateIfNotNullString(cdto.getEventDescription(), eventMod::setEventDescription);
            updateIfNotNullString(cdto.getBoardPosition(), eventMod::setBoardPosition);

            // Save the updated Event_Info
            eventInfoDao.save(eventMod);
            System.out.println("Modified EventInfo: " + eventMod);

            return true; // Modification successful
        } catch (Exception e) {
            e.printStackTrace(); // For debugging, consider logging in production
            return false; // Return false if there was an error
        }
    }

    public boolean deleteEvent(CreateEventDTO eventDetails) {
        try {
            if (eventDetails.getUuid() != null) {
                UUID uuid = UUID.fromString(eventDetails.getUuid());
                eventInfoDao.deleteById(uuid);
                eventsDao.deleteById(uuid);
                return true;
            }
            return false; // If UUID is null, return false
        } catch (Exception e) {
            e.printStackTrace(); // For debugging, consider logging in production
            return false; // Return false if there was an error
        }
    }

    public void populateAndStoreEventInfo(JSONObject eventDetails) {
        // Implement logic to populate event information
    }

    public boolean addEvent(CreateEventDTO eventDetails) {
        if (eventDetails == null || eventDetails.getEventName() == null) {
            return false; // Ensure valid event details
        }

        Events event = new Events();
        Event_Info eventInfo = new Event_Info();
        try {
            // Set event properties
            event.setEventName(eventDetails.getEventName());
            event.setEventLocation(eventDetails.getEventLocation());
            event.setEventRegistration(eventDetails.isEventRegistration());
            event.setEventStartDate(eventDetails.getEventStartDate());
            event.setEventEndDate(eventDetails.getEventEndDate());
            event.setEventStartTime(eventDetails.getEventStartTime());
            event.setEventEndTime(eventDetails.getEventEndTime());

            // Set event info properties
            eventInfo.setEventAvailability(eventDetails.getEventAvailability());
            eventInfo.setEventDescription(eventDetails.getEventDescription());
            eventInfo.setBoardPosition(eventDetails.getBoardPosition());
            UUID uuid = UUID.randomUUID(); // Use UUID directly
            eventInfo.setEventNum(uuid);

            // Save event and event info
            eventsDao.save(event);
            eventInfoDao.save(eventInfo);

            return true; // Event added successfully
        } catch (Exception e) {
            e.printStackTrace(); // For debugging, consider logging in production
            return false; // Return false if there was an error
        }
    }

    public List<Events> getAllEvents() {
        try {
            return eventsDao.findAll(); // Fetch the list of events from the DAO
        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }
}
