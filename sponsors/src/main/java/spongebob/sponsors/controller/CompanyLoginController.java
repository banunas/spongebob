package spongebob.sponsors.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spongebob.sponsors.service.CompanyService;

/**
 * 기업 로그인 / 로그아웃
 *
 * GET  /company/login  → 로그인 폼 화면
 * POST /company/login  → 로그인 처리 (세션 저장)
 * POST /company/logout → 세션 무효화 후 /company/login 리다이렉트
 */
@Controller
@RequestMapping("/company")
public class CompanyLoginController {

    private final CompanyService companyService;

    public CompanyLoginController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        // TODO: "company/login" 뷰 반환
        throw new UnsupportedOperationException("TODO");
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam int companyId,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        // TODO:
        // 1. companyService.authenticate(companyId, password) 호출
        //    → 일치하면 Company 객체 반환, 불일치면 null 또는 예외
        // 2. 인증 성공 시:
        //    session.setAttribute("companyId", companyId) 저장
        //    session.setAttribute("companyName", company.getCompanyName()) 저장
        //    return "redirect:/company/requests"
        // 3. 인증 실패 시:
        //    model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.")
        //    return "company/login"
        throw new UnsupportedOperationException("TODO");
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // TODO: session.invalidate() 후 "redirect:/company/login" 반환
        throw new UnsupportedOperationException("TODO");
    }
}
