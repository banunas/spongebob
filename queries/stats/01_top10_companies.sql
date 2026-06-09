-- 담당: 팀원 2·4
-- 기업별 평균 평점 TOP 10
-- 사용 기법: GROUP BY + AVG + RANK()
SELECT *
FROM (
    SELECT
        c.company_id,
        c.company_name,
        COUNT(rv.review_id)                         AS review_count,
        ROUND(AVG(rv.rating), 2)                    AS avg_rating,
        RANK() OVER (ORDER BY AVG(rv.rating) DESC)  AS rnk
    FROM company             c
    JOIN sponsorship_request sr ON sr.company_id = c.company_id
    JOIN review              rv ON rv.request_id  = sr.request_id
    WHERE sr.status = 'REVIEWED'
    GROUP BY c.company_id, c.company_name
) ranked
WHERE rnk <= 10
ORDER BY rnk;