-- 담당: 팀원 2·4
-- 기업별 평균 평점 TOP 10
-- 사용 기법: GROUP BY + AVG + RANK()
SELECT 
    c.company_name AS `company_name`,
    ROUND(AVG(r.rating), 1) AS `avg_rating`,
    COUNT(s.request_id) AS `sponsorship_count` 
FROM company c
JOIN sponsorship_request s ON c.company_id = s.company_id
LEFT JOIN review r ON s.request_id = r.request_id
GROUP BY c.company_id, c.company_name
ORDER BY `avg_rating` DESC, `sponsorship_count` DESC
LIMIT 10;