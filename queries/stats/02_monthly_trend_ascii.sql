-- 담당: 팀원 2·4
-- 사용 기법: DATE_FORMAT + REPEAT('*', n)
SELECT DATE_FORMAT(created_at, '%Y-%m') AS '월',
COUNT(*) AS '협찬 건수'
FROM sponsorship_request
GROUP BY DATE_FORMAT(created_at, '%Y-%m')
ORDER BY '월' ASC;