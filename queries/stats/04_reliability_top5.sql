-- 담당: 팀원 2·4
-- 신뢰도 높은 기업 TOP 5
-- 종합 점수 계산 공식: 평점×0.6 + 건수×0.4 가중치
SELECT
	c.company_name AS '기업명',
	ROUND(AVG(r.rating), 2) AS '평균 평점',
	COUNT(DISTINCT s.request_id) AS '협찬 건수',
	ROUND((AVG(r.rating)*0.6)+(COUNT(DISTINCT s.request_id)*0.4),2) AS '종합 점수'
FROM company c
LEFT JOIN sponsorship_request s ON c.company_id=s.company_id
LEFT JOIN review r ON s.request_id=r.request_id
GROUP BY c.company_id, c.company_name
ORDER BY '종합 점수' desc
LIMIT 5;