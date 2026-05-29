-- 담당: 팀원 2·4
-- 전월 대비 증감률
-- 전월과 협찬 건수 비교하기
SELECT 
	DATE_FORMAT(TM.match_date, '%Y-%m') AS '월',
	TM.cnt AS '이번달_건수',
	PM.cnt AS '전월_건수',
	(TM.cnt-PM.cnt) AS '건수 차이',
	ROUND((TM.cnt-PM.cnt)/PM.cnt*100,2) AS '증감률'
FROM 
-- 이번달 협찬 건수
		(SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS match_date, COUNT(*) AS cnt
		FROM sponsorship_request
		GROUP BY match_date) TM
LEFT JOIN 
-- 조인해서 지난달 협찬건수 구하기
	(SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS match_date, COUNT(*) AS cnt
	FROM sponsorship_request
	GROUP BY MATCH_date) PM
ON TM.match_date = DATE_ADD(PM.match_date, interval 1 MONTH)
ORDER BY TM.match_date ASC;