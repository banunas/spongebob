
--  DCL 데이터 보안 관련 추가(6/1 서아)
 
USE sponsors;
 
-- =====================================================
--  1. 앱 전용 계정 (Spring Boot 앱이 DB 접근할 때 사용)
--     SELECT, INSERT, UPDATE 허용 / DELETE 제한
--     → 데이터 삭제는 관리자(root)만 가능하도록 보안 강화
-- =====================================================
CREATE USER IF NOT EXISTS 'app_user'@'localhost' IDENTIFIED BY 'app1234';
 
GRANT SELECT, INSERT, UPDATE ON sponsors.* TO 'app_user'@'localhost';
 
-- =====================================================
--  2. 읽기 전용 계정 (통계 쿼리 시연, DBeaver 조회 전용)
--     SELECT만 허용 → 데이터 변경 불가
-- =====================================================
CREATE USER IF NOT EXISTS 'readonly_user'@'localhost' IDENTIFIED BY 'read1234';
 
GRANT SELECT ON sponsors.* TO 'readonly_user'@'localhost';
 
-- =====================================================
--  3. 학생단체 전용 계정 (DB 보안 시나리오 시연용)
--     역할: 행사 등록, 협찬 요청, 후기 작성
--     허용: SELECT, INSERT
--     차단: UPDATE → 승인/거절 불가 (기업 권한)
-- =====================================================
CREATE USER IF NOT EXISTS 'student_user'@'localhost' IDENTIFIED BY 'student1234';
 
GRANT SELECT, INSERT ON sponsors.event TO 'student_user'@'localhost';
GRANT SELECT, INSERT ON sponsors.sponsorship_request TO 'student_user'@'localhost';
GRANT SELECT, INSERT ON sponsors.review TO 'student_user'@'localhost';
GRANT SELECT ON sponsors.company TO 'student_user'@'localhost';
GRANT SELECT ON sponsors.student_org TO 'student_user'@'localhost';
 
-- =====================================================
--  4. 기업 전용 계정 (DB 보안 시나리오 시연용)
--     역할: 협찬 요청 승인/거절
--     허용: SELECT, UPDATE (sponsorship_request만)
--     차단: INSERT → 협찬 요청 직접 등록 불가 (학생단체 권한)
--           review INSERT 불가 → 후기는 학생단체만 작성
-- =====================================================
CREATE USER IF NOT EXISTS 'company_user'@'localhost' IDENTIFIED BY 'company1234';
 
GRANT SELECT ON sponsors.* TO 'company_user'@'localhost';
GRANT UPDATE ON sponsors.sponsorship_request TO 'company_user'@'localhost';
 
-- =====================================================
--  5. REVOKE 시나리오 (권한 회수 예시)
--     필요 시 아래 주석 해제하여 실행
-- =====================================================
-- 학생단체가 실수로 review를 잘못 등록한 경우 INSERT 권한 회수
-- REVOKE INSERT ON sponsors.review FROM 'student_user'@'localhost';
 
-- 기업 계정의 UPDATE 권한 회수 (계약 종료 등)
-- REVOKE UPDATE ON sponsors.sponsorship_request FROM 'company_user'@'localhost';
 
-- =====================================================
--  6. 권한 적용 및 확인
-- =====================================================
FLUSH PRIVILEGES;
 
-- 각 계정 권한 확인 (시연 시 실행)
-- SHOW GRANTS FOR 'student_user'@'localhost';
-- SHOW GRANTS FOR 'company_user'@'localhost';
-- SHOW GRANTS FOR 'app_user'@'localhost';
-- SHOW GRANTS FOR 'readonly_user'@'localhost';