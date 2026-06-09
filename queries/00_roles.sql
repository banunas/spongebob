-- =====================================================
--  데이터 보안 DCL 추가 (서아 6/1)
-- =====================================================

USE sponsors;

-- 학생단체 계정 (행사 등록, 협찬 요청, 후기 작성)
-- UPDATE 권한 없음 → 협찬 승인/거절 불가
CREATE USER IF NOT EXISTS 'student_user'@'localhost' IDENTIFIED BY 'student1234';
GRANT SELECT, INSERT ON sponsors.event TO 'student_user'@'localhost';
GRANT SELECT, INSERT ON sponsors.sponsorship_request TO 'student_user'@'localhost';
GRANT SELECT, INSERT ON sponsors.review TO 'student_user'@'localhost';
GRANT SELECT ON sponsors.company TO 'student_user'@'localhost';
GRANT SELECT ON sponsors.student_org TO 'student_user'@'localhost';

-- 기업 계정 (협찬 요청 승인/거절)
-- INSERT 권한 없음 → 협찬 요청 직접 등록 불가
CREATE USER IF NOT EXISTS 'company_user'@'localhost' IDENTIFIED BY 'company1234';
GRANT SELECT ON sponsors.* TO 'company_user'@'localhost';
GRANT UPDATE ON sponsors.sponsorship_request TO 'company_user'@'localhost';

-- 권한 적용
FLUSH PRIVILEGES;

-- 권한 확인
-- SHOW GRANTS FOR 'student_user'@'localhost';
-- SHOW GRANTS FOR 'company_user'@'localhost';