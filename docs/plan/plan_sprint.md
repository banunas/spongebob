# 협찬 통합 관리 시스템 — 스프린트 계획

**날짜**: 2026-05-17

---

### Sprint 1 — 설계 확정 + 환경 세팅 (5/11~5/17)

> **목표:** schema freeze 완료, Spring 실행 확인

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S1-1 | ERD 최종 확정 (drawio) | 전원 | 5개 테이블, 관계, 제약조건 확정 |
| S1-2 | Spring Initializr 프로젝트 생성 | 팀원 1 | pom.xml, application.yml 세팅 완료 |
| S1-3 | MariaDB 로컬 설치 + DB 생성 | 팀원 1 | `CREATE DATABASE sponsorship` 확인 |
| S1-4 | Spring → MariaDB 연결 확인 | 팀원 1 | localhost:8080 접속 + DB 연결 성공 |
| S1-5 | 00_schema.sql 작성 | 팀원 4 | 5개 테이블 생성, 제약조건·인덱스 포함 |
| S1-6 | Spring Boot 튜토리얼 (CRUD 1개) | 전원 | 각자 로컬에서 화면 동작 확인 |
| S1-7 | GitHub 레포 생성 + 브랜치 전략 합의 | 팀원 1 | develop 브랜치 + feature 브랜치 구조 완성 |

**확인 포인트:** MariaDB 실행 → DBeaver에서 `schema.sql` 실행 → 테이블 5개 생성 확인

---

### Sprint 2 — 기본 구현 (5/18~5/24)

> **목표:** 협찬요청 등록·목록 동작, 쿼리 4개 이상 실행

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S2-1 | AuthInterceptor 구현 | 팀원 1 | 로그인 없이 접근 시 /login 리다이렉트 |
| S2-2 | GlobalExceptionHandler 구현 | 팀원 1 | SponsorshipException 발생 시 에러 화면 출력 |
| S2-3 | MatchingController/Service/Repository 기본 | 팀원 1 | 상태 변경 버튼 클릭 → DB 상태 업데이트 확인 |
| S2-4 | RequestController/Service/Repository | 팀원 3 | 협찬요청 등록·목록 화면 동작 |
| S2-5 | Thymeleaf 기본 레이아웃 | 팀원 3 | 공통 헤더·메뉴 화면 적용 |
| S2-6 | seed 분포 명세서 작성 | 팀원 2 | 월별·기업별 분포 기준 문서화 → 팀원 4 전달 |
| S2-7 | 00_seed.sql 작성 | 팀원 4 | 기업 10개, 협찬 50건, 6개월 분산 삽입 |
| S2-8 | 00_roles.sql 작성 | 팀원 4 | GRANT/REVOKE DBeaver에서 실행 확인 |
| S2-9 | 01_top10_companies.sql 초안 | 팀원 4 | DBeaver에서 결과 확인 |
| S2-10 | 03·04·05번 쿼리 초안 | 팀원 2 | DBeaver에서 결과 확인 |

**확인 포인트:** 협찬요청 등록 → 목록 출력 / 쿼리 4개 이상 DBeaver 실행 성공

---

### Sprint 3 — 완성 + 통합 (5/25~5/31)

> **목표:** 전체 흐름 시연 가능, 통계 6개 화면 출력

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S3-1 | MatchingService 트랜잭션 완성 | 팀원 1 | 잘못된 상태 전이 시 롤백 확인 |
| S3-2 | ReviewController/Service/Repository | 팀원 1 | DONE 상태에서만 후기 등록 가능 확인 |
| S3-3 | 후기 중복 방지 검증 | 팀원 1 | 같은 요청에 같은 reviewer_type 중복 시 에러 처리 |
| S3-4 | 03·04·05번 쿼리 완성 | 팀원 2 | 결과값 검증 완료 |
| S3-5 | StatsController/Service/Repository | 팀원 2 | 통계 6개 화면 출력 |
| S3-6 | Stats 빈 데이터 처리 | 팀원 2 | 데이터 없을 때 "데이터 없음" 표시 (500 없음) |
| S3-7 | 02번 쿼리 완성 | 팀원 3 | ASCII 바차트 출력 확인 |
| S3-8 | Thymeleaf 전체 화면 연결 | 팀원 3 | 모든 기능 화면에서 동작 |
| S3-9 | 06_success_rate.sql 완성 | 팀원 4 | DBeaver에서 결과 확인 |
| S3-10 | 인덱스 추가 | 팀원 4 | status, created_at, company_id, request_id 인덱스 적용 |

**확인 포인트:** 협찬요청 등록 → 상태 전이 → DONE → 후기 작성 전체 흐름 / 통계 6개 화면 출력

---

### Sprint 4 — 마무리 + 발표 (6/1~6/7)

> **목표:** 로컬에서 전체 흐름 시연 가능

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S4-1 | 통합 테스트 + 버그 수정 | 전원 | 주요 흐름 에러 없음 |
| S4-2 | queries/README.md 작성 | 팀원 4 | SQL 파일 실행 순서 문서화 |
| S4-3 | README.md 작성 | 전원 | 실행 방법, 팀원 기여도 포함 |
| S4-4 | 발표 자료 작성 | 전원 | 시연 시나리오 포함 |
| S4-5 | 발표 리허설 | 전원 | 로컬 시연 성공 |
| S4-6 | 개인 기술 블로그 (velog) | 전원 | 담당 기능 정리 게시 |

**확인 포인트:** 로컬에서 전체 흐름 시연 가능
