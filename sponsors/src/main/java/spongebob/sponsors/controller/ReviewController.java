package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spongebob.sponsors.service.ReviewService;

// 담당: 서아
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 후기 작성 폼 화면
    @GetMapping("/new")
    public String form(@RequestParam int requestId, Model model) {
        model.addAttribute("requestId", requestId);
        return "review/form";
    }

    // 후기 등록 처리
    @PostMapping
    public String create(@RequestParam int requestId,
                         @RequestParam int rating,
                         @RequestParam(required = false) String comment) {
        reviewService.createReview(requestId, rating, comment);
        return "redirect:/requests";
    }
}
