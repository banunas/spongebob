package spongebob.sponsors.repository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

// 담당: 서아
@Repository
public class StatsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;

    public StatsRepository(JdbcTemplate jdbcTemplate, ResourceLoader resourceLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = resourceLoader;
    }

    private String loadSql(String fileName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:queries/stats/" + fileName);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("SQL 파일을 읽는데 실패했습니다: " + fileName, e);
        }
    }

    // 01: 기업별 평균 평점 TOP 10 (GROUP BY + AVG + RANK)
    public List<Map<String, Object>> findTop10Companies() {
        String sql = loadSql("01_top10_companies.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 02: 월별 협찬 건수 ASCII 바차트 (DATE_FORMAT + REPEAT)
    public List<Map<String, Object>> findMonthlyTrend() {
        String sql = loadSql("02_monthly_trend_ascii.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 03: 전월 대비 증감률 (LAG)
    public List<Map<String, Object>> findMomGrowthRate() {
        String sql = loadSql("03_mom_growth_rate.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 04: 신뢰도 높은 기업 TOP 5 (평점×0.6 + 건수×0.4 가중치)
    public List<Map<String, Object>> findReliabilityTop5() {
        String sql = loadSql("04_reliability_top5.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 05: 함께 협찬한 기업 추천 (서브쿼리 + JOIN)
    public List<Map<String, Object>> findCompanyRecommendation() {
        String sql = loadSql("05_company_recommendation.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 06: 기업별 협찬 성사율 (CASE WHEN + GROUP BY)
    public List<Map<String, Object>> findSuccessRate() {
        String sql = loadSql("06_success_rate.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 07: 기업별 평점 분포 및 별점 차트 (AVG + REPEAT)
    public List<Map<String, Object>> findRatingDistribution() {
        String sql = loadSql("07_rating_distribution.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 08: 연관 조직 기반 협찬 기업 조회 (다중 JOIN + IN 서브쿼리)
    public List<Map<String, Object>> findRelatedCompanies() {
        String sql = loadSql("08_related_companies.sql");
        return jdbcTemplate.queryForList(sql);
    }

    // 09: 일정 리뷰 수 이상인 기업 필터링 (GROUP BY + HAVING)
    public List<Map<String, Object>> findCompaniesReviewCountFiltering() {
        String sql = loadSql("09_companies_review_count_filtering.sql");
        return jdbcTemplate.queryForList(sql);
    }
}
