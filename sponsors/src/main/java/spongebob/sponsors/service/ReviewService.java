package spongebob.sponsors.service;

import org.springframework.stereotype.Service;
import spongebob.sponsors.common.SponsorshipException;
import spongebob.sponsors.model.Review;
import spongebob.sponsors.repository.MatchingRepository;
import spongebob.sponsors.repository.ReviewRepository;

// 담당: 서아
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MatchingRepository matchingRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         MatchingRepository matchingRepository) {
        this.reviewRepository = reviewRepository;
        this.matchingRepository = matchingRepository;
    }

    // 후기 등록
    public void createReview(int requestId, int rating, String comment) {

        // 1. 평점 검사하기
        if (rating < 1 || rating > 5) {
            throw new SponsorshipException("평점은 1~5 사이 정수여야 합니다.");
        }
        // 2. 현재 상태 조회
        String status = matchingRepository.findStatusById(requestId);

        // 3. status 검사
        switch (status) {
            case "PENDING"  -> throw new SponsorshipException("아직 승인 대기 중인 요청입니다.");
            case "APPROVED" -> throw new SponsorshipException("협찬이 진행 중입니다. 완료 후 후기를 작성할 수 있습니다.");
            case "REJECTED" -> throw new SponsorshipException("거절된 협찬 요청입니다.");
            case "REVIEWED" -> throw new SponsorshipException("이미 후기가 등록된 요청입니다.");
        }

        // 3. 후기 저장 (트리거가 자동으로 REVIEWED로 변경)
        Review review = new Review();
        review.setRequestId(requestId);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
    } 
}
