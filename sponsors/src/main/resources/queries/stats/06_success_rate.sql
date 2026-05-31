-- 담당: 팀원 2·4
-- 기업별 협찬 성사율
-- 사용 기법: CASE WHEN + GROUP BY
SELECT 
    c.company_id,
    c.company_name AS `company_name`,
    COUNT(*) AS `total_requests`,
    SUM(CASE WHEN sr.status IN ('APPROVED', 'DONE', 'REVIEWED') THEN 1 ELSE 0 END) AS `approved_count`,
    SUM(CASE WHEN sr.status = 'PENDING' THEN 1 ELSE 0 END) AS `pending_count`,
    SUM(CASE WHEN sr.status = 'REJECTED' THEN 1 ELSE 0 END) AS `rejected_count`,
    ROUND(
        SUM(CASE WHEN sr.status IN ('APPROVED', 'DONE', 'REVIEWED') THEN 1.0 ELSE 0 END)
        / COUNT(*) * 100
    , 1) AS `success_rate`
FROM company c
JOIN sponsorship_request sr ON sr.company_id = c.company_id
GROUP BY c.company_id, c.company_name
ORDER BY `success_rate` DESC, `total_requests` DESC;