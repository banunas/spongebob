-- 담당: 팀원 2·4 (원본) / 서아 (LAG 개선)
-- 전월 대비 증감률
-- 사용 기법: LAG 윈도우 함수
SELECT
    DATE_FORMAT(match_date, '%Y-%m') AS '월',
    cnt AS '이번달_건수',
    LAG(cnt) OVER (ORDER BY match_date) AS '전월_건수',
    cnt - LAG(cnt) OVER (ORDER BY match_date) AS '건수_차이',
    ROUND(
        (cnt - LAG(cnt) OVER (ORDER BY match_date))
        / LAG(cnt) OVER (ORDER BY match_date) * 100
    , 2) AS '증감률'
FROM (
    SELECT
        DATE_FORMAT(created_at, '%Y-%m-01') AS match_date,
        COUNT(*) AS cnt
    FROM sponsorship_request
    WHERE YEAR(created_at) = 2026
    GROUP BY match_date
) monthly
WHERE match_date >= '2026-02-01'
ORDER BY match_date ASC;