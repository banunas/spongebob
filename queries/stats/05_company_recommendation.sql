-- 담당: 팀원 2·4
-- 함께 협찬한 기업 추천
-- 사용 기법: 서브쿼리 + JOIN
SELECT
    c.company_id,
    c.company_name,
    COUNT(DISTINCT sr_co.event_id)   AS co_sponsor_count,
    ROUND(AVG(rv.rating), 2)         AS avg_rating
FROM sponsorship_request sr_co
JOIN company c ON c.company_id = sr_co.company_id
LEFT JOIN sponsorship_request sr_rv
       ON sr_rv.company_id = sr_co.company_id
      AND sr_rv.status     = 'REVIEWED'
LEFT JOIN review rv ON rv.request_id = sr_rv.request_id
WHERE
    sr_co.event_id IN (
        SELECT event_id
        FROM   sponsorship_request
        WHERE  company_id = 1
          AND  status IN ('APPROVED','DONE','REVIEWED')
    )
    AND sr_co.company_id != 1
    AND sr_co.status IN ('APPROVED','DONE','REVIEWED')
GROUP BY c.company_id, c.company_name
ORDER BY co_sponsor_count DESC, avg_rating DESC
LIMIT 10;