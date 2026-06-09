-- 담당: 서아
-- 월별 협찬 건수(2026만)
-- 사용 기법: DATE_FORMAT + REPEAT('*', n)
SELECT 
    DATE_FORMAT(created_at, '%Y-%m') AS '월',
    COUNT(*) AS '협찬 건수',
    REPEAT('*', COUNT(*)) AS '추이'
FROM sponsorship_request
WHERE YEAR(created_at) = 2026
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY `월` ASC;