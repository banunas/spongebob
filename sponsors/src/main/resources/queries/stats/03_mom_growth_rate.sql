-- 담당: 서아
-- 전월 대비 증감률(2026만) -> 100점 만점으로 수정 (6/1)
-- 전월과 협찬 건수 비교하기
SELECT
    c.company_name AS `company_name`,
    ROUND(AVG(r.rating), 2) AS `avg_rating`,
    COUNT(DISTINCT s.request_id) AS `sponsorship_count`,
    ROUND(
        (AVG(r.rating) / 5 * 0.6 + 
         COUNT(DISTINCT s.request_id) / (SELECT MAX(cnt) FROM (SELECT COUNT(*) AS cnt FROM sponsorship_request GROUP BY company_id) t) * 0.4) * 100
    , 2) AS `reliability_score`
FROM company c
LEFT JOIN sponsorship_request s ON c.company_id = s.company_id
LEFT JOIN review r ON s.request_id = r.request_id
WHERE c.company_name != CONVERT(c.company_name USING ascii)
GROUP BY c.company_id, c.company_name
ORDER BY `reliability_score` DESC
LIMIT 5;