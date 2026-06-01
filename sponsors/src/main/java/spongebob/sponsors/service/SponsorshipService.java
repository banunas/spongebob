package spongebob.sponsors.service;

import org.springframework.stereotype.Service;
import spongebob.sponsors.model.SponsorshipRequest;
import spongebob.sponsors.model.SponsorshipRequestView;
import spongebob.sponsors.repository.SponsorshipRepository;

import java.util.List;

// 담당: 수빈
@Service
public class SponsorshipService {

    // 로그인 없이 이화여대 컴퓨터공학과 학생회로 고정 (org_id = 1)
    private static final int ORG_ID = 1;

    private final SponsorshipRepository sponsorshipRepository;

    public SponsorshipService(SponsorshipRepository sponsorshipRepository) {
        this.sponsorshipRepository = sponsorshipRepository;
    }

    // 협찬요청 목록 조회 (커밋 org_id = 1 필터)
    public List<SponsorshipRequestView> getRequestList() {
        return sponsorshipRepository.findByOrgId(ORG_ID);
    }

    // 협찬요청 등록
    public void register(SponsorshipRequest request) {
        // 저장하기 전에 상태를 "PENDING"으로 강제 설정!
        request.setStatus("PENDING");
        sponsorshipRepository.save(request);
    }

    // 협찬요청 단건 조회 (수정 폼용)
    public SponsorshipRequest getRequest(Long requestId) {
        return sponsorshipRepository.findById(requestId);
    }

    // 협찬요청 수정 (행사·기업·수량)
    public void updateRequest(SponsorshipRequest request) {
        sponsorshipRepository.updateContent(request);
    }

    // 협찬요청 삭제
    public void deleteRequest(Long requestId) {
        sponsorshipRepository.delete(requestId);
    }

}
