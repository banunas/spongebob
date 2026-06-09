##  최근 업데이트 (2026-05-30)
**[FEAT] 기업-학생회 협찬 매칭 시스템 오류 수정 및 프로세스 통합**

- **상태 표준화:** 데이터 정합성을 위해 협찬 상태값을 표준화(ACCEPTED, REJECTED)하고 예외 처리를 강화함.
- **매칭 시스템 고도화:** 실시간 매칭 기록 추적 및 비즈니스 로직(후기 작성 등) 실행 전 상태 검증 도입.
- **트랜잭션 통합:** 후기 작성과 매칭 상태 변경을 원자적 트랜잭션으로 구성하여 데이터 무결성 확보.
- **UI 최적화:** 기업/학생회용 사용자 화면을 상태 기반으로 동적 구현.

##  향후 개발 계획
- **후기수 필터링 로직 분리:** 협찬 목록 페이지와 분석 페이지 기능 분리.
- **데이터 보안 시나리오 구축:** 데이터 접근 제어(ACID) 및 권한 정책 강화.
- **시스템 성능 검증:** 대량 데이터 유입 시의 트랜잭션 처리량(TPS) 측정 및 병목 개선.

# 협찬 통합 관리 시스템

학생단체-기업 간 협찬 요청·매칭·평가를 통합 관리하는 웹 애플리케이션.
데이터베이스 수업 팀 프로젝트 — 팀 스폰지밥

---

## 기술 스택

| 구분 | 기술 |
|------|------|
| DB | MariaDB 10.6 |
| 백엔드 | Spring Boot 3.3 + JDBC Template |
| 프론트엔드 | Thymeleaf |
| 빌드 | Maven |
| 협업 | Git + GitHub |

---

## 실행 방법

### 사전 준비

- Java 17 이상
- MariaDB 10.6

### 1. DB 세팅

```sql
-- DBeaver 또는 HeidiSQL에서 실행
CREATE DATABASE sponsorship;
```

이후 순서대로 실행:

```
queries/00_schema.sql   -- 테이블 생성
queries/00_seed.sql     -- 샘플 데이터
queries/00_roles.sql    -- 권한 설정
```

### 2. application.yml 설정

`src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/sponsorship
    username: root
    password: 본인_비밀번호
```

### 3. 실행

IntelliJ에서 `SponsorshipApplication.java` 실행
→ `localhost:8080` 접속

---

## 프로젝트 구조

```
sponsorship-db/
├── queries/
│   ├── 00_schema.sql          # DDL
│   ├── 00_seed.sql            # 샘플 데이터
│   ├── 00_roles.sql           # GRANT/REVOKE
│   └── stats/
│       ├── 01_top10_companies.sql
│       ├── 02_monthly_trend_ascii.sql
│       ├── 03_mom_growth_rate.sql
│       ├── 04_reliability_top5.sql
│       ├── 05_company_recommendation.sql
│       └── 06_success_rate.sql
├── src/main/java/com/sponsorship/
│   ├── model/                 # POJO (RowMapper용)
│   ├── controller/
│   ├── service/
│   ├── repository/
│   └── common/                # AuthInterceptor, GlobalExceptionHandler
└── src/main/resources/
    ├── application.yml
    └── templates/             # Thymeleaf HTML
```

---

## DB 설계

테이블 5개:

| 테이블 | 설명 |
|--------|------|
| student_org | 학생단체 |
| event | 행사 |
| company | 기업 |
| sponsorship_request | 협찬요청 (N:M 해소) |
| review | 후기 |

협찬요청 상태 흐름:
```
PENDING → APPROVED → DONE → REVIEWED
       ↘ REJECTED
```

---

## 팀원 기여도

| 팀원 | 담당 |
|------|------|
| 팀원 1 | Spring 세팅, 매칭 워크플로우, 후기 |
| 팀원 2 | 통계 쿼리 (LAG, 서브쿼리, 가중치) |
| 팀원 3 | 협찬요청 CRUD, Thymeleaf 화면 |
| 팀원 4 | DB 설계 (schema, seed, roles), 기초 통계 쿼리 |

---

## 기여 가이드

[CONTRIBUTING.md](./CONTRIBUTING.md) 참조
