# 협찬 통합 관리 시스템 — 역할 분담

**날짜**: 2026-05-18

> 스프린트 상세 계획: `plan_sprint.md` 참조

---

## 팀원별 담당

### 서윤 — 환경 세팅 + 매칭 워크플로우

| 작업 | 설명 |
|------|------|
| Spring 프로젝트 세팅 | Spring Initializr, application.properties |
| GlobalExceptionHandler | @ControllerAdvice, SponsorshipException |
| MatchingController/Service/Repository | 협찬요청 INSERT 직후 랜덤 APPROVED/REJECTED 결정 |
| AutoDoneScheduler | @Async — APPROVED 후 10초 뒤 DONE 자동 전환 |

**평가 연결:** 트랜잭션, 예외처리, @Async 비동기 처리

---

### 수빈 — 협찬요청 CRUD

| 작업 | 설명 |
|------|------|
| SponsorshipController/Service/Repository | 협찬요청 등록, 목록 조회 (커밋 org_id=1 필터) |
| 03_mom_growth_rate.sql | LAG() 윈도우 함수 — 전월 대비 증감률 |

**평가 연결:** SQL CRUD, JOIN, LAG() 윈도우 함수

---

### 아영 — 행사 CRUD

| 작업 | 설명 |
|------|------|
| EventController/Service/Repository | 행사 등록, 목록 조회 |
| 04_reliability_top5.sql | 평점×0.6 + 건수×0.4 가중치 — 신뢰도 높은 기업 TOP 5 |

**평가 연결:** SQL CRUD, 가중치 계산

---

### 서아 — 후기 CRUD

| 작업 | 설명 |
|------|------|
| ReviewController/Service/Repository | 후기 등록, DONE 조건 검증, 후기 완료 시 REVIEWED 전환 |
| 01_top10_companies.sql | GROUP BY + AVG + RANK() — 기업별 평균 평점 TOP 10 |
| 05_company_recommendation.sql | 서브쿼리 + JOIN — 함께 협찬한 기업 추천 |
| 06_success_rate.sql | GROUP BY + CASE WHEN — 기업별 협찬 성사율 |

**평가 연결:** 트랜잭션, 상태 검증, RANK(), 서브쿼리, CASE WHEN

---

### 전원 — 통계

| 작업 | 설명 |
|------|------|
| StatsController/Service/Repository | 통계 6개 화면 연결, 빈 데이터 시 "데이터가 없습니다" |
| 02_monthly_trend_ascii.sql | DATE_FORMAT + REPEAT('*', n) — 월별 ASCII 바차트 |

**평가 연결:** SQL 집계, 화면 연동

---

### AI — 프론트엔드 전체

| 작업 |
|------|
| layout.html (공통 레이아웃·네비) |
| home.html |
| request/form.html, request/list.html |
| event/form.html, event/list.html |
| review/form.html |
| stats/index.html |

---

## 통계 쿼리 분배

| 파일 | 담당 | SQL 기법 |
|------|------|---------|
| 01_top10_companies.sql | 서아 | GROUP BY + AVG + RANK() |
| 02_monthly_trend_ascii.sql | 전원 | DATE_FORMAT + REPEAT |
| 03_mom_growth_rate.sql | 수빈 | LAG() 윈도우 함수 |
| 04_reliability_top5.sql | 아영 | 가중치 계산 |
| 05_company_recommendation.sql | 서아 | 서브쿼리 + JOIN |
| 06_success_rate.sql | 서아 | GROUP BY + CASE WHEN |

---

## Git 브랜치 전략

```
main
└── develop
    ├── feature/db-setup         (전원: schema, seed, roles)
    ├── feature/spring-setup     (서윤: 환경, 예외처리)
    ├── feature/event            (아영: 행사 CRUD)
    ├── feature/sponsorship      (수빈: 협찬요청 CRUD)
    ├── feature/matching         (서윤: 상태 전이, @Async)
    ├── feature/review           (서아: 후기 CRUD)
    └── feature/stats            (전원: 통계 쿼리 + 화면)
```

커밋 메시지 형식: `[기능] 작업 내용`
예: `[stats] LAG 윈도우 함수 쿼리 추가`

---

## 핵심 컨벤션

- **비즈니스 로직은 Service에, DB 접근은 Repository에만**
- **컨트롤러 내 try-catch 금지** — GlobalExceptionHandler 사용
- **JPA 사용 금지** — JDBC Template + Raw SQL
- **후기 작성 조건** — ReviewService에서 status = DONE 검증 후 INSERT, 완료 시 REVIEWED 전환
- **커밋 고정** — 로그인 없이 이화여대 컴퓨터공학과 학생회 (org_id = 1) 고정
