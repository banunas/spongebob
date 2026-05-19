# queries/ 실행 가이드

## 실행 순서

반드시 아래 순서대로 실행할 것. 순서 어기면 외래키 오류 발생.

```
1. 00_schema.sql   ← DB·테이블 생성
2. 00_seed.sql     ← 샘플 데이터 삽입
3. 00_roles.sql    ← 사용자 권한 설정
4. stats/*.sql     ← 통계 쿼리 (순서 무관)
```

---

## 파일별 설명

| 파일 | 내용 |
|------|------|
| `00_schema.sql` | DB 생성, 테이블 5개, 제약조건, 인덱스 |
| `00_seed.sql` | 학생단체 5개, 기업 10개, 행사 15개, 협찬요청 55건, 후기 14건 |
| `00_roles.sql` | app_user (앱용), readonly_user (조회 전용) 계정 생성 및 권한 부여 |
| `stats/` | 통계 쿼리 6개 |

---

## 주의사항

### 재실행 시
`00_schema.sql`을 다시 실행하면 기존 데이터가 모두 날아감.
재실행 전 반드시 팀원에게 공유할 것.

```sql
-- 초기화가 필요한 경우에만 실행
DROP DATABASE IF EXISTS sponsorship;
```

### 00_seed.sql
`00_schema.sql` 실행 후 바로 이어서 실행할 것.
중간에 다른 INSERT를 먼저 하면 request_id가 어긋나서 후기 데이터가 잘못된 건에 연결됨.

### 00_roles.sql
- 비밀번호(`app1234`, `read1234`)는 로컬 개발용. 실제 서비스라면 변경 필요.
- `application-local.properties`의 username/password와 일치시킬 것.

```properties
# application-local.properties 예시
spring.datasource.username=app_user
spring.datasource.password=app1234
```

### stats/*.sql
- DBeaver 또는 HeidiSQL에서 직접 실행.
- `00_seed.sql`이 먼저 삽입되어 있어야 의미 있는 결과가 나옴.
- `readonly_user` 계정으로 실행하는 것을 권장.

---

## 계정 정보 (로컬 개발용)

| 계정 | 비밀번호 | 권한 | 용도 |
|------|---------|------|------|
| `app_user` | `app1234` | SELECT, INSERT, UPDATE | Spring Boot 앱 |
| `readonly_user` | `read1234` | SELECT | 통계 쿼리 시연, DBeaver 조회 |
