package spongebob.sponsors.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spongebob.sponsors.model.SponsorshipRequest;
import spongebob.sponsors.model.SponsorshipRequestView;
import spongebob.sponsors.repository.SponsorshipRepository;

@Service
public class CompanyService {

    private final SponsorshipRepository sponsorshipRepository;

    public CompanyService(SponsorshipRepository sponsorshipRepository) {
        this.sponsorshipRepository = sponsorshipRepository;
    }
    public List<SponsorshipRequestView> getRequestsByCompanyId(Long companyId) {
        return sponsorshipRepository.findByCompanyId(companyId);
    }
    
    // 상태 변경 로직
    @Transactional
    public void updateRequestStatus(Long requestId, String status, Long companyId) {
        // 1. 요청 조회
        SponsorshipRequest request = sponsorshipRepository.findById(requestId);
        
        // 2. 권한 확인 (해당 기업이 받은 요청이 맞는지)
        if (!request.getCompanyId().equals(companyId)) {
            throw new IllegalStateException("본인 기업의 요청만 처리할 수 있습니다.");
        }
        
        // 3. 상태 업데이트
        request.setStatus(status);
        sponsorshipRepository.update(request);
    }
}
