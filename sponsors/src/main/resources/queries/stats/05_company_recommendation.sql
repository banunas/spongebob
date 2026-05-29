-- 담당: 팀원 2·4
-- 함께 협찬한 기업 추천
-- 사용 기법: 서브쿼리 + JOIN
-- 담당: 팀원 2·4 (전체 관점 수정본)
-- 사용 기법: Self Join 기반 동시 참여 기업 조합 통계
SELECT 
    c1.company_name AS `company_name`,          -- 기준 기업
    c2.company_name AS `co_sponsor_company`,    -- 함께 참여한 기업
    COUNT(*) AS `co_sponsor_count`              -- 함께 참여한 횟수
FROM sponsorship_request sr1
JOIN sponsorship_request sr2 
    ON sr1.event_id = sr2.event_id              -- 같은 행사에 참여했고
   AND sr1.company_id < sr2.company_id          -- 중복 조합 제거 (A-B와 B-A 중 하나만)
JOIN company c1 ON sr1.company_id = c1.company_id
JOIN company c2 ON sr2.company_id = c2.company_id
WHERE sr1.status IN ('APPROVED', 'DONE', 'REVIEWED')
  AND sr2.status IN ('APPROVED', 'DONE', 'REVIEWED')
GROUP BY c1.company_id, c2.company_id, c1.company_name, c2.company_name
ORDER BY `co_sponsor_count` DESC
LIMIT 5;