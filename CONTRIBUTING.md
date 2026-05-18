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
    ├── feature/db-setup         # 팀원 4: schema, seed, roles
    ├── feature/spring-setup     # 팀원 1: 환경, 인터셉터, 예외처리
    ├── feature/matching-review  # 팀원 1: 매칭, 후기
    ├── feature/stats-report     # 팀원 2: 통계
    ├── feature/request-crud     # 팀원 3: 협찬요청 CRUD
    └── feature/thymeleaf-ui     # AI: 화면
```

- 기능 완성 후 PR → `develop` 머지
- `main`은 최종 발표본만 머지

---

## 로컬 개발 환경 세팅

### 최초 1회

```
1. MariaDB 10.6 설치 (mariadb.org)
2. DBeaver 또는 HeidiSQL 설치
3. DBeaver에서 localhost:3306 접속 확인
4. sponsorship 데이터베이스 생성
5. queries/00_schema.sql 실행
6. queries/00_seed.sql 실행
7. queries/00_roles.sql 실행
8. git clone 후 vscode에서 프로젝트 열기
9. src/main/resources/application.yml 접속 정보 확인
10. Spring Boot 실행 → localhost:8080 접속 확인
```

### application.yml 접속 정보

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/sponsorship
    username: root
    password: 본인_비밀번호
```

### 매번 개발 시작할 때

```
1. MariaDB 실행 확인 (서비스 자동 실행 설정 권장)
2. vscode에서 Spring Boot 실행
3. localhost:8080 접속
```

### schema 변경이 있을 때

```sql
DROP DATABASE sponsorship;
CREATE DATABASE sponsorship;
```
이후 schema.sql → seed.sql → roles.sql 순서로 다시 실행

> **Schema Freeze: 5/17(토) 이후 컬럼명·타입 변경 금지. 변경 시 팀 전원 동의 필수.**

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
