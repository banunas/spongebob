package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spongebob.sponsors.service.ReviewService;

// 담당: 서아
@Controller
@RequestMapping("/requests")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    // 후기 작성 여기는 나중에 수빈이 하고 추가 예정
}
