package spongebob.sponsors.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.SponsorshipRequestView;

import java.util.List;
import java.util.Optional;

/**
 * 기업 전용 협찬요청 레포지터리
 *
 * ★ 핵심: @Qualifier("companyJdbcTemplate") 로 company_user DataSource 를 사용
 *         → DB 레벨에서 UPDATE (status) 권한을 가진 계정으로만 실행됨
 *         → 다른 레포지터리는 org_user 계정이라 이 테이블 UPDATE 자체가 불가
 */
@Repository
public class CompanyRequestRepository {

    private final JdbcTemplate companyJdbcTemplate;

    public CompanyRequestRepository(@Qualifier("companyJdbcTemplate") JdbcTemplate companyJdbcTemplate) {
        this.companyJdbcTemplate = companyJdbcTemplate;
    }

    /**
     * 특정 기업으로 온 협찬요청 목록 조회 (행사명 JOIN)
     * TODO:
     * SELECT sr.request_id, sr.event_id, e.event_name,
     *        sr.company_id, sr.status, sr.quantity, sr.created_at
     * FROM sponsorship_request sr
     * JOIN event e ON e.event_id = sr.event_id
     * WHERE sr.company_id = ?
     * ORDER BY sr.created_at DESC
     */
    public List<SponsorshipRequestView> findByCompanyId(int companyId) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 단건 조회 (승인/거절 전 검증용)
     * TODO:
     * SELECT request_id, company_id, status
     * FROM sponsorship_request
     * WHERE request_id = ?
     */
    public Optional<SponsorshipRequestView> findRequestById(int requestId) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 협찬요청 상태 변경 (APPROVED / REJECTED)
     * TODO:
     * UPDATE sponsorship_request SET status = ? WHERE request_id = ?
     *
     * ★ company_user 계정은 status 컬럼만 UPDATE 가능 (GRANT UPDATE (status))
     *    다른 컬럼 변경 시도 시 DB에서 직접 거부됨
     */
    public void updateStatus(int requestId, String status) {
        throw new UnsupportedOperationException("TODO");
    }
}
