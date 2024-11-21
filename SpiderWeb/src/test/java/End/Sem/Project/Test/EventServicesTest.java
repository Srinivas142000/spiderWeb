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
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServicesTest {

    @Mock
    private EventsDao eventsDao;

    @Mock
    private EventInfoDao eventInfoDao;

    @InjectMocks
    private EventServices eventServices;

    private CreateEventDTO createEventDTO;
    private UUID eventUUID;
    private Events event;
    private EventInfo eventInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

    @Test
    void modifyEvent_success() {
        when(eventsDao.findById(eventUUID)).thenReturn(Optional.of(event));
        when(eventInfoDao.findById(eventUUID)).thenReturn(Optional.of(eventInfo));

        boolean result = eventServices.modifyEvent(createEventDTO);

        assertTrue(result);
        verify(eventsDao).save(event);
        verify(eventInfoDao).save(eventInfo);
    }

    @Test
    void modifyEvent_eventNotFound() {
        when(eventsDao.findById(eventUUID)).thenReturn(Optional.empty());
        
        assertFalse(eventServices.modifyEvent(createEventDTO));
    }

    @Test
    void deleteEvent_success() {
        when(eventInfoDao.findById(eventUUID)).thenReturn(Optional.of(eventInfo));
        when(eventsDao.findById(eventUUID)).thenReturn(Optional.of(event));

        boolean result = eventServices.deleteEvent(createEventDTO);

        assertTrue(result);
        verify(eventInfoDao).deleteById(eventUUID);
        verify(eventsDao).deleteById(eventUUID);
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
        verify(eventsDao).save(any(Events.class));
        verify(eventInfoDao).save(any(EventInfo.class));
    }

    @Test
    void addEvent_invalidEventDetails() {
        createEventDTO.setEventName(null);
        boolean result = eventServices.addEvent(createEventDTO);
        assertFalse(result);
    }

    @Test
    void getAllEvents() {
        List<Events> eventsList = new ArrayList<>();
        eventsList.add(event);
        when(eventsDao.findAll()).thenReturn(eventsList);

        List<Events> result = eventServices.getAllEvents();
        assertEquals(1, result.size());
        assertEquals(event.getEventName(), result.get(0).getEventName());
    }
}
