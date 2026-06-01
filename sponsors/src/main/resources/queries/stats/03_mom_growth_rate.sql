-- 담당: 서아
-- 전월 대비 증감률(2026만) -> 100점 만점으로 수정 (6/1)
-- 전월과 협찬 건수 비교하기
SELECT 
    DATE_FORMAT(TM.match_date, '%Y-%m') AS '월',
    TM.cnt AS '이번달_건수',
    PM.cnt AS '전월_건수',
    (TM.cnt-PM.cnt) AS '건수 차이',
    ROUND((TM.cnt-PM.cnt)/PM.cnt*100,2) AS '증감률'
FROM 
    (SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS match_date, COUNT(*) AS cnt
    FROM sponsorship_request
    WHERE YEAR(created_at) = 2026
    GROUP BY match_date) TM
LEFT JOIN 
    (SELECT DATE_FORMAT(created_at, '%Y-%m-01') AS match_date, COUNT(*) AS cnt
    FROM sponsorship_request
    GROUP BY match_date) PM
ON TM.match_date = DATE_ADD(PM.match_date, INTERVAL 1 MONTH)
WHERE TM.match_date >= '2026-02-01'
ORDER BY TM.match_date ASC;