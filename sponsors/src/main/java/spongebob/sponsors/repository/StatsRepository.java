package spongebob.sponsors.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// 담당: 전원
@Repository
public class StatsRepository {

    private final JdbcTemplate jdbcTemplate;

    public StatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 01: 기업별 평균 평점 TOP 10 (GROUP BY + AVG + RANK)
    public List<Map<String, Object>> findTop10Companies() {
        // TODO
        return null;
    }

    // 02: 월별 협찬 건수 ASCII 바차트 (DATE_FORMAT + REPEAT)
    public List<Map<String, Object>> findMonthlyTrend() {
        // TODO
        return null;
    }

    // 03: 전월 대비 증감률 (LAG)
    public List<Map<String, Object>> findMomGrowthRate() {
        // TODO
        return null;
    }

    // 04: 신뢰도 높은 기업 TOP 5 (가중치)
    public List<Map<String, Object>> findReliabilityTop5() {
        // TODO
        return null;
    }

    // 05: 함께 협찬한 기업 추천 (서브쿼리 + JOIN)
    public List<Map<String, Object>> findCompanyRecommendation() {
        // TODO
        return null;
    }

    // 06: 기업별 협찬 성사율 (CASE WHEN + GROUP BY)
    public List<Map<String, Object>> findSuccessRate() {
        // TODO
        return null;
    }
}
