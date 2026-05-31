-- 담당: 팀원 2·4
-- 사용 기법: DATE_FORMAT + REPEAT('*', n)
SELECT 
    DATE_FORMAT(created_at, '%Y-%m') AS `month`,
    COUNT(*) AS `count`,
    REPEAT('★', COUNT(*)) AS `bar`
FROM sponsorship_request
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY `month` ASC;