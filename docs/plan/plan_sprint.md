# 협찬 통합 관리 시스템 — 스프린트 계획

**날짜**: 2026-05-18 (최종 마감: 2026-06-02)

---

## 팀원 역할 요약

| 팀원 | 담당 |
|------|------|
| 서윤 | Spring 환경·매칭 (GlobalExceptionHandler, MatchingController/Service/Repository, AutoDoneScheduler) |
| 수빈 | 협찬요청 (SponsorshipController/Service/Repository) |
| 아영 | 행사 (EventController/Service/Repository) |
| 서아 | 후기 (ReviewController/Service/Repository) |
| 전원 | 통계 (StatsController/Service/Repository, 01~06번 쿼리) |
| AI   | 프론트엔드 전체 (Thymeleaf HTML 파일) |

---

## Sprint 1 — 설계 확정 + 환경 세팅 (5/11~5/17) ✅ 완료

> **목표:** schema freeze 완료, Spring DB 연결 확인

| # | 태스크 | 담당 | 상태 |
|---|--------|------|------|
| S1-1 | ERD 최종 확정 | 전원 | ✅ |
| S1-2 | 00_schema.sql 작성 | 전원 | ✅ |
| S1-3 | 00_seed.sql 작성 (학단 10개·기업 10개·협찬 55건) | 전원 | ✅ |
| S1-4 | 00_roles.sql 작성 (app_user, readonly_user) | 전원 | ✅ |
| S1-5 | Spring Initializr 세팅 (Maven·Java 17·3.5.14) | 서윤 | ✅ |
| S1-6 | MariaDB 연결 확인 (localhost:8080 + DB 연결) | 서윤 | ✅ |
| S1-7 | GitHub 레포 + 브랜치 전략 합의 | 전원 | ✅ |

---

## Sprint 2 — 기본 구현 (5/18~5/24) ← 현재

> **목표:** 협찬요청 등록·목록·행사등록·목록 동작 확인

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S2-1 | GlobalExceptionHandler + SponsorshipException | 서윤 | 예외 발생 시 에러 화면 출력 |
| S2-2 | MatchingController/Service/Repository 기본 | 서윤 | 협찬요청 INSERT 직후 랜덤 APPROVED/REJECTED 전환 확인 |
| S2-3 | AutoDoneScheduler (@Async) | 서윤 | APPROVED 후 10초 뒤 DONE 전환 확인 |
| S2-4 | SponsorshipController/Service/Repository | 수빈 | 협찬요청 등록·목록 조회 동작 (커밋 org_id=1 필터) |
| S2-5 | EventController/Service/Repository | 아영 | 행사 등록·목록 조회 동작 |
| S2-6 | 01_top10_companies.sql 초안 | 서아 | DBeaver에서 결과 확인 |
| S2-7 | 06_success_rate.sql 초안 | 서아 | DBeaver에서 결과 확인 |
| S2-8 | model/ 패키지 전체 작성 | 전원 | StudentOrg·Event·Company·SponsorshipRequest·Review.java |
| S2-9 | AI: 기본 레이아웃 + 협찬요청·행사 화면 | AI | layout.html, request/form·list.html, event/form·list.html |

**확인 포인트:** 협찬요청 등록 → 목록 출력 → 10초 후 DONE 전환 / 행사 등록 → 목록 출력

---

## Sprint 3 — 완성 + 통합 (5/25~5/31)

> **목표:** 전체 흐름 시연 가능, 통계 6개 화면 출력

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S3-1 | MatchingService @Transactional 완성 | 서윤 | 잘못된 상태 전이 시 롤백 확인 |
| S3-2 | SponsorshipService 중복 요청 검증 완성 | 수빈 | 같은 행사+기업 중복 시 에러 메시지 |
| S3-3 | ReviewController/Service/Repository | 서아 | DONE 상태에서만 후기 등록 → status REVIEWED 전환 확인 |
| S3-4 | 03_mom_growth_rate.sql (LAG) | 수빈 | DBeaver에서 결과 확인 |
| S3-5 | 04_reliability_top5.sql (가중치) | 아영 | DBeaver에서 결과 확인 |
| S3-6 | 05_company_recommendation.sql (서브쿼리) | 서아 | DBeaver에서 결과 확인 |
| S3-7 | 02_monthly_trend_ascii.sql (DATE_FORMAT) | 전원 | ASCII 바차트 출력 확인 |
| S3-8 | StatsController/Service/Repository | 전원 | 통계 6개 화면 출력, 빈 데이터 시 "데이터가 없습니다" |
| S3-9 | AI: 후기·통계·홈 화면 + 상태 한글 표시 | AI | review/form.html, stats/index.html, 상태 한글 배지 |

**확인 포인트:** 협찬요청 등록 → APPROVED → DONE → 후기작성 → REVIEWED 전체 흐름 / 통계 6개 화면 출력

---

## Sprint 4 — 마무리 + 발표 (6/1~6/2)

> **목표:** 로컬 시연 성공, 발표 자료 완성

| # | 태스크 | 담당 | 완료 조건 |
|---|--------|------|---------|
| S4-1 | 통합 테스트 + 버그 수정 | 전원 | 전체 흐름 에러 없음 |
| S4-2 | README.md 작성 | 전원 | 실행 방법, 팀원 기여도 포함 |
| S4-3 | 발표 자료 작성 | 전원 | 시연 시나리오 포함 |
| S4-4 | 발표 리허설 | 전원 | 로컬 시연 성공 |
| S4-5 | 개인 기술 블로그 (velog) | 전원 | 담당 기능 정리 게시 |

**확인 포인트:** 로컬에서 전체 흐름 시연 가능 (최종 마감 6/2)
