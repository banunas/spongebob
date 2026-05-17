# 협찬 통합 관리 시스템 — 역할 분담

**날짜**: 2026-05-17

> 스프린트 상세 계획: `plan_sprint.md` 참조

---

## 팀원별 담당

### 팀원 1  — 환경 세팅 + 매칭 워크플로우

| 작업 | 설명 |
|------|------|
| Spring 프로젝트 세팅 | Spring Initializr, application.yml |
| AuthInterceptor | 세션 기반 로그인 체크, 전체 경로 적용 |
| GlobalExceptionHandler | @ControllerAdvice, SponsorshipException |
| MatchingController/Service/Repository | 상태 전이, 트랜잭션 |
| ReviewController/Service/Repository | 후기·평점 CRUD, DONE 조건 검증 |

**평가 연결:** 트랜잭션, 예외처리, 상태 전이 로직

---

### 팀원 2  — 통계·리포트

| 작업 | 설명 |
|------|------|
| StatsController/Service/Repository | 통계 화면 6개 연결 |
| 03_mom_growth_rate.sql | LAG() 윈도우 함수 |
| 04_reliability_top5.sql | 평점×0.6 + 건수×0.4 가중치 |
| 05_company_recommendation.sql | 서브쿼리 + JOIN |
| Stats 빈 데이터 처리 | "데이터 없음" 메시지 (500 방지) |
| seed 분포 명세서 작성 → 팀원 4 전달 | 통계 쿼리 요구사항 기준 |

**평가 연결:** SQL 활용2 — 윈도우 함수, 서브쿼리, 가중치

---

### 팀원 3  — 화면 + 협찬요청 CRUD

| 작업 | 설명 |
|------|------|
| Thymeleaf 템플릿 전체 | th:each, th:text, th:if 활용 |
| RequestController/Service/Repository | 협찬요청 등록·목록·검색 |
| 02_monthly_trend_ascii.sql | DATE_FORMAT + REPEAT('*', n) |

**평가 연결:** SQL 활용1 (CRUD, JOIN), 화면 품질

---

### 팀원 4  — DB 설계 + 기초 통계 쿼리

| 작업 | 설명 |
|------|------|
| 00_schema.sql | DDL — 테이블, 제약조건, 인덱스 |
| 00_seed.sql | 샘플 데이터 (팀원 2 분포 명세 기반) |
| 00_roles.sql | GRANT/REVOKE — 역할별 권한 분리 |
| 01_top10_companies.sql | GROUP BY + AVG + RANK() |
| 06_success_rate.sql | GROUP BY + CASE WHEN |

**평가 연결:** 물리적 모델링(인덱스·제약조건), DCL, SQL 집계

---

## 통계 쿼리 분배

| 파일 | 담당 | SQL 기법 |
|------|------|---------|
| 01_top10_companies.sql | 팀원 4 | GROUP BY + AVG + RANK() |
| 02_monthly_trend_ascii.sql | 팀원 3 | DATE_FORMAT + REPEAT |
| 03_mom_growth_rate.sql | 팀원 2 | LAG() 윈도우 함수 |
| 04_reliability_top5.sql | 팀원 2 | 가중치 계산 |
| 05_company_recommendation.sql | 팀원 2 | 서브쿼리 + JOIN |
| 06_success_rate.sql | 팀원 4 | GROUP BY + CASE WHEN |

---

## Git 브랜치 전략

```
main
└── develop
    ├── feature/db-setup         (팀원 4)
    ├── feature/spring-setup     (팀원 1)
    ├── feature/matching-review  (팀원 1)
    ├── feature/stats-report     (팀원 2)
    ├── feature/request-crud     (팀원 3)
    └── feature/thymeleaf-ui     (팀원 3)
```

커밋 메시지 형식: `[기능] 작업 내용`
예: `[stats] LAG 윈도우 함수 쿼리 추가`

---

## 핵심 컨벤션

- **비즈니스 로직은 Service에, DB 접근은 Repository에만**
- **컨트롤러 내 try-catch 금지** — GlobalExceptionHandler 사용
- **컨트롤러 내 세션 체크 금지** — AuthInterceptor 사용
- **JPA 사용 금지** — JDBC Template + Raw SQL
- **후기 작성 조건** — ReviewService에서 status = DONE 검증 후 INSERT
