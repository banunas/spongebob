-- 담당: 팀원 2·4
-- 신뢰도 높은 기업 TOP 5(국내기업만)
-- 종합 점수 계산 공식: 평점×0.6 + 건수×0.4 가중치
SELECT 
    c.company_name AS `company_name`,
    ROUND(AVG(r.rating), 2) AS `avg_rating`,
    COUNT(DISTINCT s.request_id) AS `sponsorship_count`,
    ROUND((AVG(r.rating) * 0.6) + (COUNT(DISTINCT s.request_id) * 0.4), 2) AS `reliability_score`
FROM company c
LEFT JOIN sponsorship_request s ON c.company_id = s.company_id
LEFT JOIN review r ON s.request_id = r.request_id
WHERE c.company_name != CONVERT(c.company_name USING ascii)
GROUP BY c.company_id, c.company_name
ORDER BY `reliability_score` DESC
LIMIT 5;