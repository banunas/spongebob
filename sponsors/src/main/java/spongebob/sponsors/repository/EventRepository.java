package spongebob.sponsors.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.Event;

import java.util.List;
import java.util.Optional;

// 담당: 아영
@Repository
public class EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Event> findAll() {
        String sql = "SELECT event_id, org_id, event_name, event_date, venue, expected_attendees " +
                     "FROM event ORDER BY event_date DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Event.class));
    }

    public List<Event> findAllByOrgId(int orgId) {
        String sql = "SELECT event_id, org_id, event_name, event_date, venue, expected_attendees " +
                     "FROM event WHERE org_id = ? ORDER BY event_date DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Event.class), orgId);
    }

    public Optional<Event> findById(int eventId) {
        String sql = "SELECT event_id, org_id, event_name, event_date, venue, expected_attendees " +
                     "FROM event WHERE event_id = ?";
        List<Event> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Event.class), eventId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public void save(Event event) {
        String sql = "INSERT INTO event (org_id, event_name, event_date, venue, expected_attendees) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                event.getOrgId(),
                event.getEventName(),
                event.getEventDate(),
                event.getVenue(),
                event.getExpectedAttendees());
    }
}
