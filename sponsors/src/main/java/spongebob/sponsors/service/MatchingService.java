package spongebob.sponsors.service;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 담당: 서아
@Service
public class MatchingService {

    private final JdbcTemplate jdbcTemplate;

    public MatchingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 1. 전체 매칭 현황 조회
     * 협찬 요청(sponsorship_request), 기업(company), 행사(event) 테이블을 조인하여 화면에 뿌릴 데이터를 가져옵니다.
     */
    public List<Map<String, Object>> getAllMatchings() {
        String sql = """
            SELECT 
                sr.request_id,
                c.company_name,
                e.event_name,
                sr.status,
                sr.created_at
            FROM sponsorship_request sr
            LEFT JOIN company c ON sr.company_id = c.company_id
            LEFT JOIN event e ON sr.event_id = e.event_id
            ORDER BY sr.created_at DESC;
            """;
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 2. 특정 협찬 요청과 행사를 매칭 (승인 처리)
     * 데이터의 안전한 수정을 위해 @Transactional 어노테이션을 추가하는 것이 좋습니다.
     */
    @Transactional
    public void createMatching(Long requestId, Long eventId) {
        String sql = """
            UPDATE sponsorship_request 
            SET event_id = ?, status = 'APPROVED' 
            WHERE request_id = ?;
            """;
        // jdbcTemplate.update()는 INSERT, UPDATE, DELETE 쿼리를 실행할 때 사용합니다.
        jdbcTemplate.update(sql, eventId, requestId);
    }

    /**
     * [추가 기능] 매칭 취소 또는 거절 처리
     */
    @Transactional
    public void cancelMatching(Long requestId) {
        String sql = """
            UPDATE sponsorship_request 
            SET event_id = NULL, status = 'REJECTED' 
            WHERE request_id = ?;
            """;
        jdbcTemplate.update(sql, requestId);
    }
}
