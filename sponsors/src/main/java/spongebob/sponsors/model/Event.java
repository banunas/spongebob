package spongebob.sponsors.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Event {
    private int eventId;
    private int orgId;
    private String eventName;
    private LocalDate eventDate;
    private String venue;
    private int expectedAttendees;
}
