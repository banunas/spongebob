package spongebob.sponsors.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spongebob.sponsors.model.Company;
import spongebob.sponsors.model.SponsorshipRequest;
import spongebob.sponsors.model.SponsorshipRequestView;
import spongebob.sponsors.repository.CompanyRepository;
import spongebob.sponsors.repository.SponsorshipRepository;

import java.util.List;

@Service
public class CompanyService {

    private final SponsorshipRepository sponsorshipRepository;
    private final CompanyRepository companyRepository;

    public CompanyService(SponsorshipRepository sponsorshipRepository, CompanyRepository companyRepository) {
        this.sponsorshipRepository = sponsorshipRepository;
        this.companyRepository = companyRepository;
    }

    // 비밀번호로 기업 인증 — 일치하면 Company 반환, 틀리면 null
    public Company login(String password) {
        return companyRepository.findByPassword(password);
    }

    public List<SponsorshipRequestView> getAllRequests() {
        return sponsorshipRepository.findAll();
    }

    public List<SponsorshipRequestView> getRequestsByCompanyId(Long companyId) {
        return sponsorshipRepository.findByCompanyId(companyId);
    }

    @Transactional
    public void updateRequestStatus(Long requestId, String status, Long companyId) {
        SponsorshipRequest request = sponsorshipRepository.findById(requestId);

        if (!request.getCompanyId().equals(companyId)) {
            throw new IllegalStateException("본인 기업의 요청만 처리할 수 있습니다.");
        }

        request.setStatus(status);
        sponsorshipRepository.update(request);
    }
}
