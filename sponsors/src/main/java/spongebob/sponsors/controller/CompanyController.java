package spongebob.sponsors.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spongebob.sponsors.service.CompanyService;

/**
 * 기업 전용 기능 — 협찬요청 목록 조회 + 승인/거절
 *
 * GET  /company/requests              → 내 회사로 온 협찬요청 목록
 * POST /company/requests/{id}/approve → 승인 (PENDING → APPROVED)
 * POST /company/requests/{id}/reject  → 거절 (PENDING → REJECTED)
 *
 * 모든 경로는 CompanyAuthInterceptor 가 세션을 검사하므로
 * 로그인하지 않은 접근은 /company/login 으로 리다이렉트된다.
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 협찬요청 목록
    @GetMapping("/requests")
    public String requests(HttpSession session, Model model) {
        // TODO:
        // 1. int companyId = (int) session.getAttribute("companyId") 로 세션에서 꺼냄
        // 2. companyService.getRequestsByCompany(companyId) 호출 → List<SponsorshipRequestView>
        // 3. model.addAttribute("requests", ...) 추가
        // 4. model.addAttribute("companyName", session.getAttribute("companyName")) 추가
        // 5. "company/requests" 뷰 반환
        throw new UnsupportedOperationException("TODO");
    }

    // 승인
    @PostMapping("/requests/{id}/approve")
    public String approve(@PathVariable int id, HttpSession session) {
        // TODO:
        // 1. int companyId = (int) session.getAttribute("companyId")
        // 2. companyService.approve(id, companyId) 호출
        //    (내 회사 요청인지 검증 + status → APPROVED)
        // 3. "redirect:/company/requests" 반환
        throw new UnsupportedOperationException("TODO");
    }

    // 거절
    @PostMapping("/requests/{id}/reject")
    public String reject(@PathVariable int id, HttpSession session) {
        // TODO:
        // 1. int companyId = (int) session.getAttribute("companyId")
        // 2. companyService.reject(id, companyId) 호출
        //    (내 회사 요청인지 검증 + status → REJECTED)
        // 3. "redirect:/company/requests" 반환
        throw new UnsupportedOperationException("TODO");
    }
}
