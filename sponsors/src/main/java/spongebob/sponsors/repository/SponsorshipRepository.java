package spongebob.sponsors.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.SponsorshipRequest;
import spongebob.sponsors.model.SponsorshipRequestView;

import java.util.List;

// 담당: 수빈
@Repository
public class SponsorshipRepository {

    private final JdbcTemplate jdbcTemplate;

    public SponsorshipRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 특정 동아리(org_id) 협찬요청 목록 조회 (행사명·기업명 JOIN)
    public List<SponsorshipRequestView> findByOrgId(int orgId) {
        String sql = """
                SELECT sr.request_id,
                       sr.event_id,    e.event_name,
                       sr.company_id,  c.company_name,
                       sr.status, sr.quantity, sr.created_at
                FROM sponsorship_request sr
                JOIN event   e ON sr.event_id   = e.event_id
                JOIN company c ON sr.company_id = c.company_id
                WHERE e.org_id = ?
                ORDER BY sr.created_at DESC
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SponsorshipRequestView.class), orgId);
    }

    // 협찬요청 등록 (status, created_at은 DB 기본값 사용)
    public void save(SponsorshipRequest request) {
        String sql = """
                INSERT INTO sponsorship_request (event_id, company_id, quantity)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                request.getEventId(),
                request.getCompanyId(),
                request.getQuantity());
    }

    // 협찬요청 단건 status 조회
    public String findStatusById(int requestId) {
        String sql = "SELECT status FROM sponsorship_request WHERE request_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, requestId);
    }
}
