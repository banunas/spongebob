package spongebob.sponsors.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.Event;

import java.util.List;

// 담당: 아영
@Repository
public class EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 행사 전체 목록 조회
    public List<Event> findAll() {
        // TODO
        return null;
    }

    // 행사 등록
    public void save(Event event) {
        // TODO
    }
}
