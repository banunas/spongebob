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
        String sql = """
                SELECT company_name, avg_rating, sponsorship_count
                FROM (
                    SELECT
                        c.company_name,
                        COUNT(rv.review_id)                        AS sponsorship_count,
                        ROUND(AVG(rv.rating), 2)                   AS avg_rating,
                        RANK() OVER (ORDER BY AVG(rv.rating) DESC) AS rnk
                    FROM company c
                    JOIN sponsorship_request sr ON sr.company_id = c.company_id
                    JOIN review              rv ON rv.request_id  = sr.request_id
                    WHERE sr.status = 'REVIEWED'
                    GROUP BY c.company_id, c.company_name
                ) ranked
                WHERE rnk <= 10
                ORDER BY rnk
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // 02: 월별 협찬 건수 ASCII 바차트 (DATE_FORMAT + REPEAT)
    public List<Map<String, Object>> findMonthlyTrend() {
        String sql = """
                SELECT
                    DATE_FORMAT(created_at, '%Y-%m')       AS month,
                    COUNT(*)                               AS count,
                    REPEAT('*', LEAST(COUNT(*), 30))       AS bar
                FROM sponsorship_request
                GROUP BY DATE_FORMAT(created_at, '%Y-%m')
                ORDER BY month
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // 03: 전월 대비 증감률 (LAG)
    public List<Map<String, Object>> findMomGrowthRate() {
        String sql = """
                SELECT
                    month,
                    count,
                    prev_count,
                    CASE
                        WHEN prev_count IS NULL THEN NULL
                        ELSE ROUND((count - prev_count) * 100.0 / prev_count, 1)
                    END AS growth_rate
                FROM (
                    SELECT
                        DATE_FORMAT(created_at, '%Y-%m')                                   AS month,
                        COUNT(*)                                                            AS count,
                        LAG(COUNT(*)) OVER (ORDER BY DATE_FORMAT(created_at, '%Y-%m'))     AS prev_count
                    FROM sponsorship_request
                    GROUP BY DATE_FORMAT(created_at, '%Y-%m')
                ) t
                ORDER BY month
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // 04: 신뢰도 높은 기업 TOP 5 (평점×0.6 + 건수×0.4 가중치)
    public List<Map<String, Object>> findReliabilityTop5() {
        String sql = """
                SELECT
                    c.company_name,
                    COUNT(rv.review_id)                                            AS sponsorship_count,
                    ROUND(AVG(rv.rating), 2)                                       AS avg_rating,
                    ROUND(AVG(rv.rating) * 0.6 + COUNT(rv.review_id) * 0.4, 2)   AS reliability_score
                FROM company c
                JOIN sponsorship_request sr ON sr.company_id = c.company_id
                JOIN review              rv ON rv.request_id  = sr.request_id
                WHERE sr.status = 'REVIEWED'
                GROUP BY c.company_id, c.company_name
                ORDER BY reliability_score DESC
                LIMIT 5
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // 05: 함께 협찬한 기업 추천 (서브쿼리 + JOIN)
    public List<Map<String, Object>> findCompanyRecommendation() {
        String sql = """
                SELECT
                    c.company_name,
                    COUNT(DISTINCT sr_co.event_id)  AS co_sponsor_count,
                    ROUND(AVG(rv.rating), 2)        AS avg_rating
                FROM sponsorship_request sr_co
                JOIN company c ON c.company_id = sr_co.company_id
                LEFT JOIN sponsorship_request sr_rv
                       ON sr_rv.company_id = sr_co.company_id
                      AND sr_rv.status     = 'REVIEWED'
                LEFT JOIN review rv ON rv.request_id = sr_rv.request_id
                WHERE
                    sr_co.event_id IN (
                        SELECT event_id
                        FROM   sponsorship_request
                        WHERE  company_id = 1
                          AND  status IN ('APPROVED','DONE','REVIEWED')
                    )
                    AND sr_co.company_id != 1
                    AND sr_co.status IN ('APPROVED','DONE','REVIEWED')
                GROUP BY c.company_id, c.company_name
                ORDER BY co_sponsor_count DESC, avg_rating DESC
                LIMIT 10
                """;
        return jdbcTemplate.queryForList(sql);
    }

    // 06: 기업별 협찬 성사율 (CASE WHEN + GROUP BY)
    public List<Map<String, Object>> findSuccessRate() {
        String sql = """
                SELECT
                    c.company_name,
                    COUNT(*)                                                                   AS total_requests,
                    SUM(CASE WHEN sr.status IN ('APPROVED','DONE','REVIEWED') THEN 1 ELSE 0 END) AS approved_count,
                    ROUND(
                        SUM(CASE WHEN sr.status IN ('APPROVED','DONE','REVIEWED') THEN 1.0 ELSE 0 END)
                        / COUNT(*) * 100
                    , 1)                                                                       AS success_rate
                FROM company c
                JOIN sponsorship_request sr ON sr.company_id = c.company_id
                GROUP BY c.company_id, c.company_name
                ORDER BY success_rate DESC, total_requests DESC
                """;
        return jdbcTemplate.queryForList(sql);
    }
}
