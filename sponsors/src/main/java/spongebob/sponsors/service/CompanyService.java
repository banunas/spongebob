package spongebob.sponsors.service;

import org.springframework.stereotype.Service;
import spongebob.sponsors.common.SponsorshipException;
import spongebob.sponsors.model.Company;
import spongebob.sponsors.model.SponsorshipRequestView;
import spongebob.sponsors.repository.CompanyRepository;
import spongebob.sponsors.repository.CompanyRequestRepository;

import java.util.List;

/**
 * 기업 전용 비즈니스 로직
 *  - 로그인 인증
 *  - 협찬요청 목록 조회
 *  - 승인 / 거절 처리
 */
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyRequestRepository companyRequestRepository;

    public CompanyService(CompanyRepository companyRepository,
                          CompanyRequestRepository companyRequestRepository) {
        this.companyRepository = companyRepository;
        this.companyRequestRepository = companyRequestRepository;
    }

    /**
     * 로그인 인증
     * TODO:
     * 1. companyRepository.findById(companyId) 로 Company 조회
     *    (CompanyRepository 에 findById 메서드가 없으면 추가 필요)
     * 2. company == null → throw new SponsorshipException("존재하지 않는 기업입니다.")
     * 3. !company.getPassword().equals(password) → throw new SponsorshipException("비밀번호가 올바르지 않습니다.")
     * 4. 통과하면 company 반환
     */
    public Company authenticate(int companyId, String password) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 내 회사로 온 협찬요청 목록 조회
     * TODO:
     * companyRequestRepository.findByCompanyId(companyId) 결과를 그대로 반환
     */
    public List<SponsorshipRequestView> getRequestsByCompany(int companyId) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 협찬요청 승인 (PENDING → APPROVED)
     * TODO:
     * 1. companyRequestRepository.findRequestById(id) 로 요청 조회
     * 2. request.getCompanyId() != companyId → throw SponsorshipException("권한이 없습니다.")
     *    (다른 회사 요청을 승인하려는 시도 방지)
     * 3. !"PENDING".equals(request.getStatus()) → throw SponsorshipException("대기 중인 요청만 처리할 수 있습니다.")
     * 4. companyRequestRepository.updateStatus(id, "APPROVED") 호출
     *    → 이 메서드는 company_user DataSource 를 통해 DB UPDATE 실행
     */
    public void approve(int requestId, int companyId) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * 협찬요청 거절 (PENDING → REJECTED)
     * TODO: approve() 와 동일한 구조, updateStatus 에 "REJECTED" 전달
     */
    public void reject(int requestId, int companyId) {
        throw new UnsupportedOperationException("TODO");
    }
}
