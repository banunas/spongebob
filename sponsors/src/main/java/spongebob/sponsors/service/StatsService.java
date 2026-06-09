package spongebob.sponsors.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import spongebob.sponsors.repository.StatsRepository;

// 담당: 서아
@Service
public class StatsService {

    private final StatsRepository statsRepository = null;

    // 01: 기업별 평균 평점 TOP 10
    public List<Map<String, Object>> getTop10Companies() {
        return statsRepository.findTop10Companies();
    }

    // 02: 월별 협찬 건수 ASCII 바차트
    public List<Map<String, Object>> getMonthlyTrend() {
        return statsRepository.findMonthlyTrend();
    }

    // 03: 전월 대비 증감률
    public List<Map<String, Object>> getMomGrowthRate() {
        return statsRepository.findMomGrowthRate();
    }

    // 04: 신뢰도 높은 기업 TOP 5 (가중치 점수 기반)
    public List<Map<String, Object>> getReliabilityTop5() {
        return statsRepository.findReliabilityTop5();
    }

    // 05: 함께 협찬한 기업 추천
    public List<Map<String, Object>> getCompanyRecommendation() {
        return statsRepository.findCompanyRecommendation();
    }

    // 06: 기업별 협찬 성사율
    public List<Map<String, Object>> getSuccessRate() {
        return statsRepository.findSuccessRate();
    }

    // 07: 기업별 평점 분포 및 별점 차트
    public List<Map<String, Object>> getRatingDistribution() {
        return statsRepository.findRatingDistribution();
    }

    // 08: 연관 조직 기반 협찬 기업 조회
    public List<Map<String, Object>> getRelatedCompanies() {
        return statsRepository.findRelatedCompanies();
    }

    // 09: 일정 리뷰 수 이상인 기업 필터링
    public List<Map<String, Object>> getCompaniesReviewCountFiltering() {
        return statsRepository.findCompaniesReviewCountFiltering();
    }
}
