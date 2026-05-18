package spongebob.sponsors.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.SponsorshipRequest;

import java.util.List;

// 담당: 수빈
@Repository
public class SponsorshipRepository {

    private final JdbcTemplate jdbcTemplate;

    public SponsorshipRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 커밋(org_id=1) 협찬요청 목록 조회 (행사명·기업명 JOIN)
    public List<SponsorshipRequest> findByOrgId(int orgId) {
        // TODO
        return null;
    }

    // 협찬요청 등록
    public void save(SponsorshipRequest request) {
        // TODO
    }

    // 협찬요청 단건 status 조회
    public String findStatusById(int requestId) {
        // TODO
        return null;
    }
}
