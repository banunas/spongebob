# 협찬 통합 관리 시스템 — ERD 설계

**날짜**: 2026-05-17

---

## 엔티티 관계도

```
┌─────────────┐                    ┌──────────────────┐
│   학생단체   │                    │       행사        │
│─────────────│                    │──────────────────│
│ 단체_ID(PK) │ 1    주최하다    N  │ 행사_ID(PK)      │
│ 단체명      │────────────────────▷│ 단체_ID(FK)      │
│ 연락처      │                    │ 행사명           │
└─────────────┘                    │ 행사날짜         │
                                   │ 행사장소         │
                                   │ 예상참여인원      │
                                   └────────┬─────────┘
                                            │ 1
                                            │ 포함된다
                                            │ N
┌─────────────┐                    ┌────────▽─────────┐
│     기업    │                    │     협찬요청      │
│─────────────│  1    수신한다   N  │──────────────────│
│ 기업_ID(PK) │◁───────────────────│ 요청_ID(PK)      │
│ 기업명      │                    │ 행사_ID(FK)      │
│ 상품명      │                    │ 기업_ID(FK)      │
│ 연락처      │                    │ 상태             │
└─────────────┘                    │ 홍보효과         │
                                   │ 홍보계획         │
                                   │ 수량             │
                                   └────────┬─────────┘
                                            │ 1
                                            │ 작성된다
                                            │ N
                                   ┌────────▽─────────┐
                                   │       후기        │
                                   │──────────────────│
                                   │ 후기_ID(PK)      │
                                   │ 요청_ID(FK)      │
                                   │ 작성자유형(ENUM)  │
                                   │ 평점             │
                                   │ 코멘트           │
                                   │ 작성일           │
                                   └──────────────────┘
                          UNIQUE(요청_ID, 작성자유형)
```

---

## 테이블 명세

### 학생단체 (student_org)

| 컬럼 (한글) | 컬럼 (영문) | 타입 | 제약조건 |
|------------|------------|------|---------|
| 단체_ID | org_id | INT | PK, AUTO_INCREMENT |
| 단체명 | org_name | VARCHAR(50) | NOT NULL |
| 연락처 | contact | VARCHAR(20) | |

### 행사 (event)

| 컬럼 (한글) | 컬럼 (영문) | 타입 | 제약조건 |
|------------|------------|------|---------|
| 행사_ID | event_id | INT | PK, AUTO_INCREMENT |
| 단체_ID | org_id | INT | FK → student_org.org_id |
| 행사명 | event_name | VARCHAR(100) | NOT NULL |
| 행사날짜 | event_date | DATE | NOT NULL |
| 행사장소 | venue | VARCHAR(100) | |
| 예상참여인원 | expected_attendees | INT | CHECK > 0 |

### 기업 (company)

| 컬럼 (한글) | 컬럼 (영문) | 타입 | 제약조건 |
|------------|------------|------|---------|
| 기업_ID | company_id | INT | PK, AUTO_INCREMENT |
| 기업명 | company_name | VARCHAR(50) | NOT NULL |
| 상품명 | product_name | VARCHAR(100) | NOT NULL |
| 연락처 | contact | VARCHAR(20) | |

### 협찬요청 (sponsorship_request)

| 컬럼 (한글) | 컬럼 (영문) | 타입 | 제약조건 |
|------------|------------|------|---------|
| 요청_ID | request_id | INT | PK, AUTO_INCREMENT |
| 행사_ID | event_id | INT | FK → event.event_id |
| 기업_ID | company_id | INT | FK → company.company_id |
| 상태 | status | ENUM | PENDING, APPROVED, REJECTED, DONE, REVIEWED |
| 수량 | quantity | INT | NOT NULL, CHECK > 0 |
| 생성일시 | created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP |

UNIQUE (event_id, company_id)

### 후기 (review)

| 컬럼 (한글) | 컬럼 (영문) | 타입 | 제약조건 |
|------------|------------|------|---------|
| 후기_ID | review_id | INT | PK, AUTO_INCREMENT |
| 요청_ID | request_id | INT | FK → sponsorship_request.request_id |
| 작성자유형 | reviewer_type | ENUM | ORG, COMPANY |
| 평점 | rating | INT | CHECK (1 <= rating <= 5) |
| 코멘트 | comment | TEXT | |
| 작성일 | created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP |

UNIQUE (request_id, reviewer_type)

---

## 관계 정리

| 관계 | 카디널리티 | 설명 |
|------|-----------|------|
| 학생단체 - 행사 | 1:N | 한 단체가 여러 행사 주최 |
| 행사 - 협찬요청 | 1:N | 한 행사에 여러 기업에 협찬 요청 가능 |
| 기업 - 협찬요청 | 1:N | 한 기업이 여러 협찬 요청 수신 |
| 협찬요청 - 후기 | 1:N | 한 요청에 단체·기업 양측 후기 가능 |
| 행사 - 기업 | N:M | 협찬요청 테이블이 중간 테이블로 해소 |

---

## 설계 결정 사항

- **학생단체 → 협찬요청 직접 관계 없음**: 협찬요청 → 행사 → 학생단체로 JOIN 연결 가능. 직접 FK 추가 시 중복으로 정규화 위반
- **기업 상품 단일 가정**: 기업 하나당 상품 하나만 제공한다고 가정, 상품명 컬럼 하나로 처리
- **후기 중복 방지**: UNIQUE(요청_ID, 작성자유형)으로 양측 각 1회만 작성 가능
- **협찬 이력 테이블**: v2에서 추가 예정 (상태 전이 로그 저장용)
- **created_at 추가**: 협찬요청에 created_at DATETIME DEFAULT CURRENT_TIMESTAMP — 월별 추이(02번), LAG 증감률(03번) 쿼리 필수
- **중복 요청 방지**: 협찬요청에 UNIQUE(행사_ID, 기업_ID) — 같은 행사에 같은 기업 중복 요청 불가
- **카테고리 컬럼 제외**: 06번 쿼리를 카테고리 없이 다른 기준(기업별 성사율 등)으로 수정 예정

## 인덱스 설계 (물리적 모델링)

schema.sql 작성 시 아래 컬럼에 INDEX 적용 필요:

| 테이블 | 컬럼 | 이유 |
|--------|------|------|
| 협찬요청 | status | 상태별 조회 빈번 |
| 협찬요청 | created_at | 월별 집계 쿼리 (DATE_FORMAT, LAG) |
| 협찬요청 | 기업_ID | JOIN 성능 |
| 후기 | 요청_ID | FK JOIN 성능 |

## GSTACK REVIEW REPORT

| Review | Trigger | Why | Runs | Status | Findings |
|--------|---------|-----|------|--------|----------|
| CEO Review | `/plan-ceo-review` | Scope & strategy | 0 | — | — |
| Codex Review | `/codex review` | Independent 2nd opinion | 1 | issues_found | — |
| Eng Review | `/plan-eng-review` | Architecture & tests (required) | 2 | CLEAR (PLAN) | 4 issues, 0 critical gaps |
| Design Review | `/plan-design-review` | UI/UX gaps | 0 | — | — |
| DX Review | `/plan-devex-review` | Developer experience gaps | 0 | — | — |

**DECISIONS:**
- D1: created_at 추가 → A (적용 완료)
- D2: 카테고리 컬럼 제외, 06번 쿼리 수정 → C (적용 완료)
- D3: UNIQUE(행사_ID, 기업_ID) 추가 → A (적용 완료)
- D4: 인덱스 목록 문서화 → A (적용 완료)

**UNRESOLVED:** 0

**VERDICT:** ENG CLEARED — 구현 시작 가능. schema.sql 작성 시 created_at, UNIQUE 제약, 인덱스 반영 필요.
