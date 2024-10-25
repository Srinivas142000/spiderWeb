package End.Sem.Project.Test;

import End.Sem.Project.DTO.CreateEventDTO;
import End.Sem.Project.Dao.EventInfoDao;
import End.Sem.Project.Dao.EventsDao;
import End.Sem.Project.Model.EventInfo;
import End.Sem.Project.Model.Events;
import End.Sem.Project.Services.EventServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EventServicesTest {
    @Mock
    private EventsDao eventsDao = Mockito.mock(EventsDao.class);

    @Mock
    private EventInfoDao eventInfoDao = Mockito.mock(EventInfoDao.class);

    @InjectMocks
    private EventServices eventServices = Mockito.mock(EventServices.class);

    private CreateEventDTO createEventDTO = Mockito.mock(CreateEventDTO.class);
    private UUID eventUUID;
    private Events event;
    private EventInfo eventInfo;

    @BeforeEach
    void setUp() {
        eventUUID = UUID.randomUUID();
        createEventDTO = new CreateEventDTO();
        createEventDTO.setUuid(eventUUID.toString());
        createEventDTO.setEventName("Sample Event");
        createEventDTO.setEventLocation("Sample Location");
        createEventDTO.setEventRegistration(true);
        createEventDTO.setEventAvailability(100);
        createEventDTO.setEventDescription("Sample Description");
        createEventDTO.setBoardPosition("Organizer");

        event = new Events();
        event.setEventName("Sample Event");
        event.setEventLocation("Sample Location");
        event.setEventRegistration(true);

        eventInfo = new EventInfo();
        eventInfo.setEventAvailability(100);
        eventInfo.setEventDescription("Sample Description");
        eventInfo.setBoardPosition("Organizer");
        eventInfo.setEventNum(eventUUID);
    }
    // This test is not necessary
//    @Test
//    void getSpecificEvent() {
//        assertNotNull(eventServices.getSpecificEvent("Sample Event"));
//    }

    @Test
    void modifyEvent_success() {
        Mockito.when(eventsDao.findById(eventUUID)).thenReturn(Optional.of(event));
        Mockito.when(eventInfoDao.findById(eventUUID)).thenReturn(Optional.of(eventInfo));
        boolean result = eventServices.modifyEvent(createEventDTO);
        assertTrue(result);
    }

    @Test
    void modifyEvent_eventNotFound() {
        Mockito.when(eventsDao.findById(eventUUID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            eventServices.modifyEvent(createEventDTO);
        });
        assertEquals("Event not found for UUID: " + createEventDTO.getUuid(), exception.getMessage());
    }

    @Test
    void deleteEvent_success() {
        Mockito.when(eventInfoDao.findById(eventUUID)).thenReturn(Optional.of(eventInfo));
        Mockito.when(eventsDao.findById(eventUUID)).thenReturn(Optional.of(event));

        boolean result = eventServices.deleteEvent(createEventDTO);
        assertTrue(result);
        Mockito.verify(eventInfoDao).deleteById(eventUUID);
        Mockito.verify(eventsDao).deleteById(eventUUID);
    }

    @Test
    void deleteEvent_eventUUIDNull() {
        createEventDTO.setUuid(null);
        boolean result = eventServices.deleteEvent(createEventDTO);
        assertFalse(result);
    }

    @Test
    void addEvent_success() {
        boolean result = eventServices.addEvent(createEventDTO);
        assertTrue(result);
        Mockito.verify(eventsDao).save(Mockito.any(Events.class));
        Mockito.verify(eventInfoDao).save(Mockito.any(EventInfo.class));
    }

    @Test
    void addEvent_invalidEventDetails() {
        createEventDTO.setEventName(null);  // Invalid scenario
        boolean result = eventServices.addEvent(createEventDTO);
        assertFalse(result);
    }

    @Test
    void getAllEvents() {
        List<Events> eventsList = new ArrayList<>();
        eventsList.add(event);
        Mockito.when(eventsDao.findAll()).thenReturn(eventsList);

        List<Events> result = eventServices.getAllEvents();
        assertEquals(1, result.size());
        assertEquals(event.getEventName(), result.get(0).getEventName());
    }
}
