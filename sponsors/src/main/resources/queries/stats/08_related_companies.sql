SELECT DISTINCT c.company_id, c.company_name
FROM event e
JOIN sponsorship_request sr ON sr.event_id = e.event_id
JOIN company c ON c.company_id = sr.company_id
WHERE e.org_id IN (
    SELECT DISTINCT e2.org_id
    FROM review rv
    JOIN sponsorship_request sr2 ON sr2.request_id = rv.request_id
    JOIN event e2 ON e2.event_id = sr2.event_id
)
AND sr.status IN ('APPROVED', 'DONE', 'REVIEWED')
ORDER BY c.company_name;