package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String form(@RequestParam(required = false) Long eventId, Model model) {
        SponsorshipRequest request = new SponsorshipRequest();
        
        // 💡 파라미터가 있으면 eventId를 미리 넣어줌
        if (eventId != null) {
            request.setEventId(eventId);
        } else {
            request.setEventId(0L);
        }
        
        model.addAttribute("request", request);
        model.addAttribute("events", eventService.getEventsForCurrentOrg());
        model.addAttribute("companies", companyRepository.findAll());
        return "request/form";
    }

    // 협찬요청 등록 처리 (예외 처리 추가)
    @PostMapping
    public String register(@ModelAttribute SponsorshipRequest request, RedirectAttributes redirectAttributes) {
        try {
            sponsorshipService.register(request);
            return "redirect:/requests";
        } catch (Exception e) {
            // 중복 요청 시 메시지 전달
            redirectAttributes.addFlashAttribute("errorMessage", "이미 해당 기업에 요청한 행사입니다.");
            return "redirect:/requests/new";
        }
    }

    // 협찬요청 수정 폼 화면
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("request", sponsorshipService.getRequest(id));
        model.addAttribute("events", eventService.getEventsForCurrentOrg());
        model.addAttribute("companies", companyRepository.findAll());
        return "request/form";
    }

    // 협찬요청 수정 처리
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute SponsorshipRequest request,
                         RedirectAttributes redirectAttributes) {
        try {
            request.setRequestId(id.intValue());
            sponsorshipService.updateRequest(request);
            return "redirect:/requests";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정 중 오류가 발생했습니다.");
            return "redirect:/requests/" + id + "/edit";
        }
    }

    // 협찬요청 삭제 처리
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        sponsorshipService.deleteRequest(id);
        return "redirect:/requests";
    }
}
