package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spongebob.sponsors.service.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/requests")
    public String list(Model model) {
        Long companyId = 1L; 
        model.addAttribute("requests", companyService.getRequestsByCompanyId(companyId));
        return "company/list";
    }

    @PostMapping("/requests/{id}/accept")
    public String accept(@PathVariable Long id) {
        companyService.updateRequestStatus(id, "ACCEPTED", 1L);
        return "redirect:/company/requests";
    }

    @PostMapping("/requests/{id}/reject")
    public String reject(@PathVariable Long id) {
        companyService.updateRequestStatus(id, "REJECTED", 1L);
        return "redirect:/company/requests";
    }
}
