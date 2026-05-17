# 협찬 통합 관리 시스템 — 프로젝트 설계 계획

**날짜**: 2026-05-07 (최종 수정: 2026-05-17)

## 배경

대학 학생회·동아리의 기업 협찬 요청 과정이 카카오톡·메일·DM·엑셀에 흩어져 있어
이력 관리·흐름 추적·성과 분석이 불가능하다. 데이터베이스 수업 팀 프로젝트로,
학생단체·기업 두 주체가 협찬 요청부터 후기까지 한 시스템에서 처리할 수 있는
DB 기반 웹 애플리케이션을 설계·구현한다.

- 기간: 약 4주
- 팀원: 3학년 4명
- 평가 배점: 프로젝트 관리 25점 / 기술 50점 / 결과물 품질 25점 / 가산점

## AS-IS

- 협찬 관련 커뮤니케이션이 카카오톡·메일·DM에 분산
- 기업별 협찬 이력 없음 → 매년 처음부터 재시작
- 후기·평점이 외부 폼에 분산 수집 → 성과 분석 불가

## TO-BE

- 협찬 요청 등록 → 기업 제안 → 승인 → 발송 → 완료 → 후기까지 단일 시스템에서 처리
- 모든 협찬 이력이 DB에 누적 → 통계·추천 기능으로 활용
- 사용자 유형별 권한 분리 (MariaDB GRANT/REVOKE)
- SQL 집계·윈도우 함수·서브쿼리로 통계 리포트 자동 생성

---

## 기술 스택

| 구분 | 선택 | 이유 |
|------|------|------|
| DB | MariaDB 10.6 | 수업 실습 환경과 동일, 윈도우 함수(RANK·LAG) 지원 |
| 백엔드 | Spring Boot 3 | 레이어 분리로 4명 역할 분담 용이, 포폴 활용 가능 |
| DB 접근 | JDBC Template | Raw SQL 직접 작성 → 수업 목표 SQL 능력 직접 발휘 |
| 프론트엔드 | Thymeleaf (서버사이드) | JS 없이 HTML만으로 화면 구성 가능 |
| DB 클라이언트 | DBeaver / HeidiSQL | 팀원별 선호 툴 사용 가능 |
| 협업 | Git + GitHub | 브랜치 전략으로 팀원별 기여도 커밋 로그에 반영 |

### 버전 고정

| 항목 | 버전 | 비고 |
|------|------|------|
| Java | 17 | Spring Boot 3 최소 요구사항, LTS |
| Spring Boot | 3.3.x | 현재 안정 버전 |
| Build Tool | Maven | 학교 프로젝트 표준, Spring Initializr에서 선택 |
| MariaDB | 10.6 | 윈도우 함수(RANK·LAG) 지원 확인된 버전 |

> 팀원 전원 `java -version` 확인 후 17 미만이면 설치 먼저 진행할 것

### 시스템 구조

```
브라우저 (localhost:8080)
    ↓ HTTP 요청
Controller  (URL 라우팅)
    ↓
Service     (비즈니스 로직)
    ↓
Repository  (JDBC Template — Raw SQL)
    ↓
MariaDB (localhost:3306)
    ↓ ResultSet
Repository → Service → Thymeleaf 렌더링 → 브라우저
```

- 개발·시연: MariaDB 로컬 설치 + IntelliJ에서 Spring Boot 실행

---

## DB 설계 (테이블 5개)

### 테이블 목록

| 테이블 (영문) | 테이블 (한글) | 설명 |
|--------------|-------------|------|
| student_org | 학생단체 | 협찬 요청 주체 |
| event | 행사 | 협찬이 필요한 행사 |
| company | 기업 | 협찬 제공 주체 |
| sponsorship_request | 협찬요청 | 행사-기업 매칭 (N:M 해소 테이블) |
| review | 후기 | 협찬 완료 후 양측 평가 |

### 관계 요약

```
학생단체(1) ──주최하다──▷ 행사(N)
행사(1)     ──포함된다──▷ 협찬요청(N)
기업(1)     ──수신한다──▷ 협찬요청(N)
협찬요청(1) ──작성된다──▷ 후기(N)
```

> 행사-기업 N:M 관계를 협찬요청이 중간 테이블로 해소

### 주요 제약조건

- `UNIQUE(event_id, company_id)` — 같은 행사에 같은 기업 중복 요청 불가
- `UNIQUE(request_id, reviewer_type)` — 양측 각 1회만 후기 작성 가능
- `CHECK(1 <= rating <= 5)` — 평점 범위 제한
- `created_at DEFAULT CURRENT_TIMESTAMP` — 협찬요청 생성일시 자동 기록

> 상세 컬럼 명세: `docs/erd/erd_plan.md` 참조

---

## 핵심 기능 목록

### 기능 1 — 협찬 요청 등록·검색
- 등록 항목: 행사명, 날짜, 장소, 예상참여인원, 필요 물품(수량)
- 기업 검색: 날짜 필터 + 정렬
- 제약조건: `CHECK`, `UNIQUE`, `NOT NULL` 적극 활용

### 기능 2 — 매칭·승인 워크플로우
- 상태 흐름:
  ```
  PENDING → APPROVED → DONE → REVIEWED
         ↘ REJECTED
  ```
- 잘못된 순서의 상태 변경 시 예외 처리·롤백
- 협찬 이력 테이블(sponsorship_history)은 v2에서 추가 예정

### 기능 3 — 후기·평점
- 상태가 DONE일 때만 후기 작성 가능 (Service 레이어에서 검증)
- 양측 상호 평가 (학생단체 → 기업, 기업 → 학생단체)
- 중복 후기 방지: `UNIQUE(request_id, reviewer_type)`

### 기능 4 — 통계·리포트 (SQL 활용2 핵심)

| 파일 | SQL 기법 | 출력 |
|------|---------|------|
| 01_top10_companies.sql | `GROUP BY` + `AVG` + `RANK()` | 기업별 평균 평점 TOP 10 |
| 02_monthly_trend_ascii.sql | `DATE_FORMAT` + `REPEAT('*', n)` | 월별 협찬 건수 ASCII 바차트 |
| 03_mom_growth_rate.sql | `LAG()` 윈도우 함수 | 전월 대비 증감률 |
| 04_reliability_top5.sql | 평점×0.6 + 건수×0.4 가중치 | 신뢰도 높은 기업 TOP 5 |
| 05_company_recommendation.sql | 서브쿼리 + `JOIN` | 함께 협찬한 기업 추천 |
| 06_success_rate.sql | `CASE WHEN` + `GROUP BY` | 기업별 협찬 성사율 |

---

## 역할 분담

### 공통 (전원 — 1주차)
ERD·모델링은 전원이 함께 진행 (평가 핵심)

### 개인 역할 (2주차~)

| 팀원 | 담당 | 주요 작업 | 평가 연결 |
|------|------|---------|---------|
| 팀원 1 | 환경·매칭·후기 | Spring 세팅, AuthInterceptor, GlobalExceptionHandler, MatchingController/Service/Repository, ReviewController/Service/Repository | 트랜잭션, 예외처리 |
| 팀원 2 | 통계·리포트 | StatsController/Service/Repository, 03·04·05번 쿼리 (LAG, 가중치, 서브쿼리) | SQL 활용2 — 윈도우 함수, 서브쿼리 |
| 팀원 3 | 화면·협찬요청 | Thymeleaf 템플릿 전체, RequestController/Service/Repository, 02번 쿼리 (DATE_FORMAT) | SQL 활용1 (CRUD, JOIN) |
| 팀원 4 | DB 설계·기초 통계 | schema.sql, seed.sql, roles.sql, 01·06번 쿼리 (GROUP BY + RANK, CASE WHEN) | 물리적 모델링, DCL |

> **Service 레이어 컨벤션**: 비즈니스 로직은 Service에, DB 접근은 Repository에만. 팀 전원 동의 필수.

### Git 브랜치 전략

```
main
└── develop
    ├── feature/db-setup         (팀원 4: schema, seed, roles)
    ├── feature/spring-setup     (팀원 1: 환경, 인터셉터, 예외처리)
    ├── feature/matching-review  (팀원 1: 매칭, 후기)
    ├── feature/stats-report     (팀원 2: 통계)
    ├── feature/request-crud     (팀원 3: 협찬요청 CRUD)
    └── feature/thymeleaf-ui     (팀원 3: 화면)
```

커밋 메시지 형식: `[기능] 작업 내용` (예: `[stats] LAG 윈도우 함수 쿼리 추가`)

---

## 프로젝트 구조

```
sponsorship-db/
├── pom.xml
├── queries/
│   ├── README.md                   # 실행 순서 안내
│   ├── 00_schema.sql               # DDL: 테이블·제약조건·인덱스
│   ├── 00_seed.sql                 # 샘플 데이터 (기업 10개·협찬 50건 이상)
│   ├── 00_roles.sql                # GRANT/REVOKE DCL
│   └── stats/
│       ├── 01_top10_companies.sql
│       ├── 02_monthly_trend_ascii.sql
│       ├── 03_mom_growth_rate.sql
│       ├── 04_reliability_top5.sql
│       ├── 05_company_recommendation.sql
│       └── 06_success_rate.sql
├── src/main/java/com/sponsorship/
│   ├── SponsorshipApplication.java
│   ├── model/
│   │   ├── StudentOrg.java
│   │   ├── Event.java
│   │   ├── Company.java
│   │   ├── SponsorshipRequest.java
│   │   └── Review.java
│   ├── controller/
│   │   ├── SponsorshipRequestController.java
│   │   ├── MatchingController.java
│   │   ├── ReviewController.java
│   │   └── StatsController.java
│   ├── service/
│   │   ├── SponsorshipService.java
│   │   ├── MatchingService.java
│   │   ├── ReviewService.java
│   │   └── StatsService.java
│   ├── repository/
│   │   ├── SponsorshipRepository.java
│   │   ├── MatchingRepository.java
│   │   ├── ReviewRepository.java
│   │   └── StatsRepository.java
│   └── common/
│       ├── AuthInterceptor.java
│       ├── GlobalExceptionHandler.java
│       └── SponsorshipException.java
└── src/main/resources/
    ├── application.yml
    ├── templates/
    └── static/
```

---

## 4주 타임라인

### 1주차 (5/11~5/17) — 설계 확정 + 환경 세팅

| 작업 | 담당 | 마감 |
|------|------|------|
| ERD 확정 (전원) | 전원 | 5/17(토) |
| schema.sql 초안 | 팀원 4 | 5/17(토) ← **schema freeze** |
| Spring 프로젝트 생성 + DB 연결 확인 | 팀원 1 | 5/17(토) |
| Spring Boot 튜토리얼 (CRUD 1개) | 전원 | 5/17(토) |

**확인 포인트:** MariaDB 실행 + Spring 실행 + localhost:8080 접속 확인

### 2주차 (5/18~5/24) — 기본 구현

| 작업 | 담당 |
|------|------|
| RequestController/Service/Repository | 팀원 3 |
| MatchingController/Service/Repository (상태 전이 기본) | 팀원 1 |
| AuthInterceptor, GlobalExceptionHandler | 팀원 1 |
| 00_seed.sql, 00_roles.sql | 팀원 4 |
| 통계 쿼리 초안 (03·04·05번) | 팀원 2 |
| 01·06번 쿼리 초안 | 팀원 4 |
| Thymeleaf 기본 템플릿 | 팀원 3 |

**확인 포인트:** 협찬요청 등록 → 목록 화면 동작 / 쿼리 4개 이상 DBeaver 실행 성공

### 3주차 (5/25~5/31) — 완성 + 통합

| 작업 | 담당 |
|------|------|
| MatchingService 트랜잭션 완성 | 팀원 1 |
| ReviewController/Service/Repository | 팀원 1 |
| 통계 쿼리 6개 완성 + StatsController 연결 | 팀원 2 |
| Stats 빈 데이터 처리 ("데이터 없음" 메시지) | 팀원 2 |
| Thymeleaf 전체 화면 연결 | 팀원 3 |
| 인덱스 추가, roles.sql 최종 | 팀원 4 |

**확인 포인트:** 협찬요청 등록 → 상태 전이 → 후기 전체 흐름 / 통계 6개 화면 출력

### 4주차 (6/1~6/7) — 마무리 + 발표

| 작업 | 담당 |
|------|------|
| 통합 테스트 + 버그 수정 | 전원 |
| README, queries/README.md | 전원 |
| 발표 자료 | 전원 |
| 개인 기술 블로그 (velog) | 전원 |

**확인 포인트:** 로컬에서 전체 흐름 시연 가능

---

## 가산점 전략

| 항목 | 대응 방법 |
|------|---------|
| 개인별 기술 블로그 | 각자 velog에 담당 기능 정리 |
| Git 기여도 명시 | 기능별 브랜치 → PR 머지 → 커밋 로그에 팀원별 기여도 자동 반영 |
| 회의록 문서화 | `docs/meetings/` 폴더에 주별 회의록 커밋 |

---

## 고려사항

- **JPA 사용 금지**: 수업 평가가 SQL 직접 작성 능력을 보기 때문에 JDBC Template 고수
- **model/ 패키지 필수**: JDBC Template은 쿼리 결과를 담을 POJO(RowMapper용) 필요. `model/` 패키지에 통일
- **로그인 구현 방식**: Spring Security 없이 `HttpSession`으로 직접 세션 관리. `AuthInterceptor` 1개로 /login 제외 전체 경로 적용 — 팀원 1 담당, 컨트롤러 내 개별 세션 체크 금지
- **예외 처리 전략**: `@ControllerAdvice`로 글로벌 에러 처리 통일 (`GlobalExceptionHandler.java` + `SponsorshipException.java`). 컨트롤러 내 try-catch 지양
- **상태 전이 검증**: `MatchingService`에서 현재 상태 조회 → 전이 유효성 검증 → `@Transactional`로 status UPDATE 원자 처리. 잘못된 전이 시 `SponsorshipException` throw
- **후기 작성 조건**: `ReviewService`에서 status = DONE 여부 검증 후 INSERT. DB 트리거 아닌 Service 레이어에서 처리
- **협찬 이력 테이블**: v2에서 추가 예정 (상태 전이 로그 저장용)
- **인덱스 설계**: `sponsorship_request.status`, `sponsorship_request.created_at`, `sponsorship_request.company_id`, `review.request_id` — 물리적 모델링 점수
- **트랜잭션 격리 수준**: 상태 변경 시 `READ COMMITTED` 명시적 설정
- **샘플 데이터**: 통계 쿼리가 의미 있게 나오려면 기업 10개·협찬 50건 이상 필요. **팀원 2가 쿼리 요구사항 기준으로 seed 분포 명세서 작성 → 팀원 4에게 전달** (월별 분산: 6개월 이상, LAG용 연속 2개월, 추천용 중복참여 기업)
- **Schema Freeze**: **5/17(토) 이후 컬럼명·타입 변경 금지.** 변경 필요 시 팀 전원 동의 필수
- **RowMapper 전략**: `BeanPropertyRowMapper` 우선 사용. 복잡한 JOIN 결과는 `RowMapper` 람다 직접 작성
- **Stats 빈 데이터 처리**: 통계 화면 데이터 없을 경우 "데이터 없음" 메시지 표시 필수 (500 방지) — 팀원 2 구현 시 주의

---

## GSTACK REVIEW REPORT

| Review | Trigger | Why | Runs | Status | Findings |
|--------|---------|-----|------|--------|----------|
| CEO Review | `/plan-ceo-review` | Scope & strategy | 0 | — | — |
| Outside Voice | `/codex review` | Independent 2nd opinion | 1 | issues_found | 6 findings (2 critical applied: schema freeze, seed distribution) |
| Eng Review | `/plan-eng-review` | Architecture & tests (required) | 1 | CLEAR | 4 issues, 1 critical gap (stats 빈데이터 처리) |
| Design Review | `/plan-design-review` | UI/UX gaps | 0 | — | — |
| DX Review | `/plan-devex-review` | Developer experience gaps | 0 | — | — |

**UNRESOLVED:** 0

**VERDICT:** ENG CLEARED — 구현 시작 가능.
