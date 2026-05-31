-- =====================================================
--  협찬 매칭 시스템 - DCL (권한 설정)
--  팀: 스폰지밥
--  날짜: 2026-05-18
--  실행 순서: 00_schema.sql → 00_seed.sql → 이 파일
-- =====================================================

USE sponsors;

-- =====================================================
--  1. 앱 전용 계정 (Spring Boot 앱이 DB 접근할 때 사용)
-- =====================================================
CREATE USER IF NOT EXISTS 'app_user'@'localhost' IDENTIFIED BY 'app1234';

GRANT SELECT, INSERT, UPDATE ON sponsors.* TO 'app_user'@'localhost';

-- =====================================================
--  2. 읽기 전용 계정 (통계 쿼리 시연, DBeaver 조회 전용)
-- =====================================================
CREATE USER IF NOT EXISTS 'readonly_user'@'localhost' IDENTIFIED BY 'read1234';

GRANT SELECT ON sponsors.* TO 'readonly_user'@'localhost';

-- =====================================================
--  3. 기업 전용 계정 (협찬요청 승인/거절 UPDATE 권한)
-- =====================================================
-- TODO: company_user 계정 생성
--   CREATE USER IF NOT EXISTS 'company_user'@'localhost' IDENTIFIED BY '비밀번호';
--
-- TODO: 권한 설정
--   - sponsorship_request 테이블의 status 컬럼만 UPDATE 가능하도록 컬럼 단위 GRANT
--   - SELECT는 필요한 테이블 전체 허용 (요청 목록 조회용)
--   GRANT SELECT ON sponsors.event              TO 'company_user'@'localhost';
--   GRANT SELECT ON sponsors.sponsorship_request TO 'company_user'@'localhost';
--   GRANT UPDATE (status) ON sponsors.sponsorship_request TO 'company_user'@'localhost';
--
-- 핵심 포인트:
--   GRANT UPDATE (status) 처럼 컬럼 단위로 권한을 줄 수 있음
--   → 기업이 request_id, event_id 등 다른 컬럼은 절대 수정 불가

-- =====================================================
--  4. 학생단체 전용 계정 (INSERT 전용, UPDATE 불가)
-- =====================================================
-- TODO: org_user 계정 생성
--   CREATE USER IF NOT EXISTS 'org_user'@'localhost' IDENTIFIED BY '비밀번호';
--
-- TODO: 권한 설정
--   - event, sponsorship_request INSERT 가능
--   - UPDATE/DELETE 는 부여하지 않음 (승인/거절은 기업만 가능)
--   GRANT SELECT, INSERT ON sponsors.event               TO 'org_user'@'localhost';
--   GRANT SELECT, INSERT ON sponsors.sponsorship_request TO 'org_user'@'localhost';
--   GRANT SELECT ON sponsors.company                     TO 'org_user'@'localhost';
--   GRANT SELECT ON sponsors.review                      TO 'org_user'@'localhost';

-- =====================================================
--  권한 적용
-- =====================================================
FLUSH PRIVILEGES;
