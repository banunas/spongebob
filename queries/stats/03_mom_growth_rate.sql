-- 서아
-- 전월 대비 증감률(개선ver. 6/1)
-- 사용 기법: DATE_FORMAT + LAG()
SELECT 
    DATE_FORMAT(created_at, '%Y-%m') AS '월',
    COUNT(*) AS '이번달_건수',
    LAG(COUNT(*)) OVER (ORDER BY DATE_FORMAT(created_at, '%Y-%m-01')) AS '전월_건수',
    (COUNT(*) - LAG(COUNT(*)) OVER (ORDER BY DATE_FORMAT(created_at, '%Y-%m-01'))) AS '건수 차이',
    ROUND((COUNT(*) - LAG(COUNT(*)) OVER (ORDER BY DATE_FORMAT(created_at, '%Y-%m-01'))) / 
          LAG(COUNT(*)) OVER (ORDER BY DATE_FORMAT(created_at, '%Y-%m-01')) * 100, 2) AS '증감률'
FROM sponsorship_request
GROUP BY DATE_FORMAT(created_at, '%Y-%m-01')
ORDER BY DATE_FORMAT(created_at, '%Y-%m-01') ASC;