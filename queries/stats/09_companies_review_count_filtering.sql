SELECT 
    c.company_id,
    c.company_name,
    COUNT(rv.review_id) AS review_count,
    ROUND(AVG(rv.rating), 2) AS avg_rating
FROM company c
JOIN sponsorship_request sr ON sr.company_id = c.company_id
JOIN review rv ON rv.request_id = sr.request_id
WHERE sr.status = 'REVIEWED'
GROUP BY c.company_id, c.company_name
HAVING review_count >= 2
ORDER BY review_count DESC;