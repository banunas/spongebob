package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spongebob.sponsors.repository.StatsRepository;

import java.util.List;

// 담당: 전원
@Controller
@RequestMapping("/stats")
public class StatsController {

    private final StatsRepository statsRepository;

    public StatsController(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("top10",          nullToEmpty(statsRepository.findTop10Companies()));
        model.addAttribute("monthly",        nullToEmpty(statsRepository.findMonthlyTrend()));
        model.addAttribute("growth",         nullToEmpty(statsRepository.findMomGrowthRate()));
        model.addAttribute("reliability",    nullToEmpty(statsRepository.findReliabilityTop5()));
        model.addAttribute("recommendation", nullToEmpty(statsRepository.findCompanyRecommendation()));
        model.addAttribute("successRate",    nullToEmpty(statsRepository.findSuccessRate()));
        return "stats/index";
    }

    private <T> List<T> nullToEmpty(List<T> list) {
        return list == null ? List.of() : list;
    }
}
