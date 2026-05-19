-- =====================================================
--  협찬 매칭 시스템 - DDL
--  실행 순서: 이 파일 → 00_seed.sql → 00_roles.sql
-- =====================================================

CREATE DATABASE IF NOT EXISTS sponsorship
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE sponsorship;

-- =====================================================
--  1. 학생단체
-- =====================================================
CREATE TABLE student_org (
    org_id      INT          NOT NULL AUTO_INCREMENT,
    org_name    VARCHAR(50)  NOT NULL,
    contact     VARCHAR(20),

    PRIMARY KEY (org_id)
);

-- =====================================================
--  2. 기업
-- =====================================================
CREATE TABLE company (
    company_id    INT           NOT NULL AUTO_INCREMENT,
    company_name  VARCHAR(50)   NOT NULL,
    product_name  VARCHAR(100)  NOT NULL,
    contact       VARCHAR(20),

    PRIMARY KEY (company_id)
);

-- =====================================================
--  3. 행사 (학생단체가 주최)
-- =====================================================
CREATE TABLE event (
    event_id            INT           NOT NULL AUTO_INCREMENT,
    org_id              INT           NOT NULL,
    event_name          VARCHAR(100)  NOT NULL,
    event_date          DATE          NOT NULL,
    venue               VARCHAR(100),
    expected_attendees  INT           CHECK (expected_attendees > 0),

    PRIMARY KEY (event_id),
    FOREIGN KEY (org_id) REFERENCES student_org(org_id),

    INDEX idx_event_org  (org_id),
    INDEX idx_event_date (event_date)
);

-- =====================================================
--  4. 협찬요청 (행사 N : 기업 M 해소 테이블)
-- =====================================================
CREATE TABLE sponsorship_request (
    request_id  INT          NOT NULL AUTO_INCREMENT,
    event_id    INT          NOT NULL,
    company_id  INT          NOT NULL,
    status      ENUM('PENDING','APPROVED','REJECTED','DONE','REVIEWED')
                             NOT NULL DEFAULT 'PENDING',
    quantity    INT          NOT NULL CHECK (quantity > 0),
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (request_id),
    FOREIGN KEY (event_id)   REFERENCES event(event_id),
    FOREIGN KEY (company_id) REFERENCES company(company_id),

    UNIQUE INDEX uq_request_event_company (event_id, company_id),
    INDEX idx_request_status     (status),
    INDEX idx_request_created_at (created_at),
    INDEX idx_request_company    (company_id)
);

-- =====================================================
--  5. 후기 (협찬요청 완료 후 양측 평가)
-- =====================================================
CREATE TABLE review (
    review_id      INT          NOT NULL AUTO_INCREMENT,
    request_id     INT          NOT NULL,
    reviewer_type  ENUM('ORG','COMPANY') NOT NULL,
    rating         INT          NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment        TEXT,
    created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (review_id),
    FOREIGN KEY (request_id) REFERENCES sponsorship_request(request_id),

    UNIQUE INDEX uq_review_request_type (request_id, reviewer_type),
    INDEX idx_review_request (request_id)
);
