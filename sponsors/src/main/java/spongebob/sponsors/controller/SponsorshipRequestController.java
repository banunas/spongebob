package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spongebob.sponsors.model.SponsorshipRequest;
import spongebob.sponsors.repository.CompanyRepository;
import spongebob.sponsors.service.EventService;
import spongebob.sponsors.service.SponsorshipService;

// 담당: 수빈
@Controller
@RequestMapping("/requests")
public class SponsorshipRequestController {

    private final SponsorshipService sponsorshipService;
    private final EventService eventService;
    private final CompanyRepository companyRepository;

    public SponsorshipRequestController(SponsorshipService sponsorshipService,
                                        EventService eventService,
                                        CompanyRepository companyRepository) {
        this.sponsorshipService = sponsorshipService;
        this.eventService = eventService;
        this.companyRepository = companyRepository;
    }

    // 협찬요청 목록 화면
    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", sponsorshipService.getRequestList());
        return "request/list";
    }

    // 협찬요청 등록 폼 화면
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("request", new SponsorshipRequest());
        model.addAttribute("events", eventService.getEventsForCurrentOrg());
        model.addAttribute("companies", companyRepository.findAll());
        return "request/form";
    }

    // 협찬요청 등록 처리
    @PostMapping
    public String register(@ModelAttribute SponsorshipRequest request) {
        sponsorshipService.register(request);
        return "redirect:/requests";
    }
}
