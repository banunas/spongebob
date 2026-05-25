package spongebob.sponsors.service;

import org.springframework.stereotype.Service;
import spongebob.sponsors.common.SponsorshipException;
import spongebob.sponsors.model.Event;
import spongebob.sponsors.repository.EventRepository;

import java.util.List;

// 담당: 아영
@Service
public class EventService {

    private static final int CURRENT_ORG_ID = 1;

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEventsForCurrentOrg() {
        return eventRepository.findAllByOrgId(CURRENT_ORG_ID);
    }

    public Event getEventById(int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new SponsorshipException("행사를 찾을 수 없습니다. id=" + eventId));
    }

    public void createEvent(Event event) {
        if (event.getEventName() == null || event.getEventName().isBlank()) {
            throw new SponsorshipException("행사명은 필수입니다.");
        }
        if (event.getEventDate() == null) {
            throw new SponsorshipException("행사 날짜는 필수입니다.");
        }
        if (event.getExpectedAttendees() <= 0) {
            throw new SponsorshipException("예상 참여 인원은 1명 이상이어야 합니다.");
        }
        event.setOrgId(CURRENT_ORG_ID);
        eventRepository.save(event);
    }
}
