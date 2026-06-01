package spongebob.sponsors.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spongebob.sponsors.model.Company;
import spongebob.sponsors.service.CompanyService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/requests")
    public String list(Model model) {
        model.addAttribute("requests", companyService.getAllRequests());
        return "company/list";
    }

    // AJAX 비밀번호 인증 — JSON 응답
    @PostMapping("/auth-json")
    @ResponseBody
    public Map<String, Object> authJson(@RequestParam String password, HttpSession session) {
        Company company = companyService.login(password);
        Map<String, Object> result = new HashMap<>();
        if (company != null) {
            @SuppressWarnings("unchecked")
            Set<Long> ids = (Set<Long>) session.getAttribute("authenticatedCompanyIds");
            if (ids == null) {
                ids = new HashSet<>();
                session.setAttribute("authenticatedCompanyIds", ids);
            }
            ids.add((long) company.getCompanyId());
            result.put("success", true);
            result.put("companyId", company.getCompanyId());
        } else {
            result.put("success", false);
        }
        return result;
    }

    @PostMapping("/requests/{id}/accept")
    public String accept(@PathVariable Long id,
                         @RequestParam Long companyId,
                         HttpSession session, RedirectAttributes ra) {
        try {
            verifyAuthenticated(companyId, session);
            companyService.updateRequestStatus(id, "APPROVED", companyId);
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/company/requests";
    }

    @PostMapping("/requests/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam Long companyId,
                         HttpSession session, RedirectAttributes ra) {
        try {
            verifyAuthenticated(companyId, session);
            companyService.updateRequestStatus(id, "REJECTED", companyId);
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/company/requests";
    }

    @SuppressWarnings("unchecked")
    private void verifyAuthenticated(Long companyId, HttpSession session) {
        Set<Long> ids = (Set<Long>) session.getAttribute("authenticatedCompanyIds");
        if (ids == null || !ids.contains(companyId)) {
            throw new IllegalStateException("인증이 필요합니다. 비밀번호를 다시 입력해주세요.");
        }
    }
}
