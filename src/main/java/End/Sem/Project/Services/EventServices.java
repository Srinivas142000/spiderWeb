package End.Sem.Project.Services;

import End.Sem.Project.DTO.CreateEventDTO;
import End.Sem.Project.Dao.EventInfoDao;
import End.Sem.Project.Dao.EventsDao;
import End.Sem.Project.Model.EventInfo;
import End.Sem.Project.Model.Events;
import End.Sem.Project.Helpers.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class responsible for managing events, including adding, modifying,
 * deleting, and retrieving event information.
 */
@Service
public class EventServices extends CommonHelpers {

    private final EventsDao eventsDao;
    private final EventInfoDao eventInfoDao;

    /**
     * Instantiates all DAO and classes
     *
     * @param eventsDao    DAO for event operations.
     * @param eventInfoDao DAO for event information operations.
     */
    @Autowired
    public EventServices(EventsDao eventsDao, EventInfoDao eventInfoDao) {
        this.eventsDao = eventsDao;
        this.eventInfoDao = eventInfoDao;
    }

    /**
     * Trying to return it as a JSON Object to see if React would like a JSON structure or not
     * Fetches the details of a specific event by its name.
     *
     * @param eventId the eventId of the event to fetch.
     * @return a JSONObject containing the event details.
     */
public JSONObject getSpecificEvent(UUID eventId) {
        JSONObject rJSON = new JSONObject();

        // Fetch event and event info by ID
        Optional<Events> eventOpt = eventsDao.findById(eventId);
        Optional<EventInfo> eventInfoOpt = eventInfoDao.findById(eventId);

        // Check if both Event and EventInfo are present
        if (eventOpt.isPresent() && eventInfoOpt.isPresent()) {
            Events event = eventOpt.get();
            EventInfo ei = eventInfoOpt.get();

            // Print the objects for debugging purposes
            System.out.println("Event: " + event);
            System.out.println("EventInfo: " + ei);

            // Populate the response JSON with event details
            rJSON.put("eventNum", event.getEventNum().toString());
            rJSON.put("eventName", event.getEventName());
            rJSON.put("eventStartDate", event.getEventStartDate().toString());
            rJSON.put("eventEndDate", event.getEventEndDate().toString());
            rJSON.put("eventLocation", event.getEventLocation());
            rJSON.put("eventStartTime", event.getEventStartTime().toString());
            rJSON.put("eventEndTime", event.getEventEndTime().toString());
            rJSON.put("eventRegistration", event.isEventRegistration());

            // Populate additional event info
            rJSON.put("eventDescription", ei.getEventDescription());
            rJSON.put("eventAvailability", ei.getEventAvailability());
        } else {
            // If either event or event info is not found, return an error
            rJSON.put("error", "Event or Event Info not found");
        }

        return rJSON;
    }

    /**
     * Modifies an existing event based on the provided CreateEventDTO.
     *
     * This method retrieves an event using the UUID from the CreateEventDTO,
     * updates its fields if they are not null, and saves the updated event
     * back to the database. It also updates related event information.
     *
     * @param cdto the data transfer object containing event details to update
     * @return true if the event was modified successfully, false otherwise
     * @throws IllegalArgumentException if the UUID in the CreateEventDTO is null
     * @throws Exception if the event or event info is not found
     */
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

            Events eventToUpdate = existingEvent.get();
            updateIfNotNullString(cdto.getEventName(), eventToUpdate::setEventName);
            updateIfNotNullString(cdto.getEventLocation(), eventToUpdate::setEventLocation);
            updateIfNotNullBool(cdto.isEventRegistration(), eventToUpdate::setEventRegistration);
            updateIfNotNullDate(cdto.getEventStartDate(), eventToUpdate::setEventStartDate);
            updateIfNotNullDate(cdto.getEventEndDate(), eventToUpdate::setEventEndDate);
            updateIfNotNullTime(cdto.getEventStartTime(), eventToUpdate::setEventStartTime);
            updateIfNotNullTime(cdto.getEventEndTime(), eventToUpdate::setEventEndTime);

            eventsDao.save(eventToUpdate);
            System.out.println("Modified Event: " + eventToUpdate);

            Optional<EventInfo> eventInfoOptional = eventInfoDao.findById(u);
            if (!eventInfoOptional.isPresent()) {
                throw new Exception("Event info not found for UUID: " + cdto.getUuid());
            }

            EventInfo eventMod = eventInfoOptional.get();
            updateIfNotNullUUID(u, eventMod::setEventNum);
            updateIfNotNullInt(cdto.getEventAvailability(), eventMod::setEventAvailability);
            updateIfNotNullString(cdto.getEventDescription(), eventMod::setEventDescription);
            updateIfNotNullString(cdto.getBoardPosition(), eventMod::setBoardPosition);

            eventInfoDao.save(eventMod);
            System.out.println("Modified EventInfo: " + eventMod);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an event based on the provided details
     *
     * @param eventDetails contains the event UUID to perform delete.
     * @return true if the deletion is successful, false if the UUID is null or an error occurs.
     */
    public boolean deleteEvent(CreateEventDTO eventDetails) {
        try {
            if (eventDetails.getUuid() != null) {
                UUID uuid = UUID.fromString(eventDetails.getUuid());
                eventInfoDao.deleteById(uuid);
                eventsDao.deleteById(uuid);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new event based CreateEventDTO from user.
     *
     * @param eventDetails containing event information to add.
     * @return true if the event is added successfully, false if event details are invalid/incorrect.
     */
    public boolean addEvent(CreateEventDTO eventDetails) {
        if (eventDetails == null || eventDetails.getEventName() == null) {
            return false;
        }

        Events event = new Events();
        EventInfo eventInfo = new EventInfo();
        try {
            event.setEventName(eventDetails.getEventName());
            event.setEventLocation(eventDetails.getEventLocation());
            event.setEventRegistration(eventDetails.isEventRegistration());
            event.setEventStartDate(eventDetails.getEventStartDate());
            event.setEventEndDate(eventDetails.getEventEndDate());
            event.setEventStartTime(eventDetails.getEventStartTime());
            event.setEventEndTime(eventDetails.getEventEndTime());

            eventInfo.setEventAvailability(eventDetails.getEventAvailability());
            eventInfo.setEventDescription(eventDetails.getEventDescription());
            eventInfo.setBoardPosition(eventDetails.getBoardPosition());
            UUID uuid = UUID.randomUUID();
            eventInfo.setEventNum(uuid);
            event.setEventNum(uuid);
            eventsDao.save(event);
            eventInfoDao.save(eventInfo);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a list of all events available across communities
     * @return a List of Events, or an empty list if an error occurs follows the DTO.
     */
    public List<Events> getAllEvents() {
        try {
            return eventsDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
