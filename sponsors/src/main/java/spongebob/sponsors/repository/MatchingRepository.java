package spongebob.sponsors.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// 담당: 서윤
@Repository
public class MatchingRepository {

    private final JdbcTemplate jdbcTemplate;

    public MatchingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 협찬요청 status 조회
    public String findStatusById(int requestId) {
        String sql = "SELECT status FROM sponsorship_request WHERE request_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, requestId);
    }

    // 협찬요청 status 변경
    public void updateStatus(int requestId, String status) {
        String sql = "UPDATE sponsorship_request SET status = ? WHERE request_id = ?";
        jdbcTemplate.update(sql, status, requestId);
    }
}
