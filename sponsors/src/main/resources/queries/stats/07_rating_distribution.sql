SELECT 
    c.company_name,
    ROUND(AVG(rv.rating), 2) AS avg_rating,
    REPEAT('★', ROUND(AVG(rv.rating))) AS bar
FROM company c
JOIN sponsorship_request sr ON sr.company_id = c.company_id
JOIN review rv ON rv.request_id = sr.request_id
WHERE sr.status = 'REVIEWED'
AND c.company_name != CONVERT(c.company_name USING ascii)
GROUP BY c.company_id, c.company_name
ORDER BY avg_rating DESC;