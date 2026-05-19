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
--  권한 적용
-- =====================================================
FLUSH PRIVILEGES;
