# 기여 가이드

## 작업 시작 전 필수 절차

매 작업 시작 전 반드시 `develop` 브랜치 최신화 후 진행해주세요.

```bash
git checkout develop
git pull origin develop
git checkout 본인_브랜치
git merge develop
```

> merge 중 충돌(conflict)이 발생하면 바로 팀 채널에 공유해주세요.

---

## 브랜치 전략

```
main
└── develop
    ├── feature/erd-modeling     # 전원, 1주차 ERD 설계
    ├── feature/db-setup         # A: schema, seed, roles, docker
    ├── feature/user-request     # B: 사용자·협찬 요청 CRUD
    ├── feature/matching-review  # C: 워크플로우·후기
    └── feature/stats-report     # D: 통계·리포트
```

- 기능 완성 후 PR → `develop` 머지
- `main`은 최종 발표본만 머지

---

## 실행 방법

### 시작
```bash
docker compose up
```
`localhost:8080` 에서 접속

### 백그라운드 실행
```bash
docker compose up -d
```

### 종료
```bash
docker compose down
```

### 로그 확인
```bash
docker compose logs -f
```

> `docker-compose` (하이픈) 아닌 `docker compose` (띄어쓰기) 사용할 것 — Docker v2 기준

---

## 커밋 메시지 규칙

```
[태그] 작업 내용
```

| 태그 | 사용 상황 |
|------|---------|
| `[feat]` | 새 기능 추가 |
| `[fix]` | 버그 수정 |
| `[db]` | SQL·스키마·데이터 관련 |
| `[docs]` | 문서 수정 |
| `[refactor]` | 기능 변경 없는 코드 정리 |
| `[test]` | 테스트 코드 |

예시:
```
[feat] 협찬 요청 CRUD 구현
[db] 월별 협찬 추이 쿼리 추가
[fix] 상태 변경 트랜잭션 롤백 오류 수정
```
