package spongebob.sponsors.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * DataSource 두 개를 정의한다.
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │  기존 레포지터리는 @Primary DataSource(org_user)를 자동 주입받음   │
 * │  CompanyRequestRepository만 @Qualifier("companyJdbcTemplate")   │
 * │  를 명시하여 company_user DataSource를 사용한다                   │
 * └─────────────────────────────────────────────────────────────────┘
 */
@Configuration
public class DataSourceConfig {

    // application.properties / application-local.properties 에서 읽어옴
    @Value("${spring.datasource.url}")
    private String defaultUrl;

    @Value("${app.datasource.company.url}")
    private String companyUrl;

    @Value("${app.datasource.company.username}")
    private String companyUsername;

    @Value("${app.datasource.company.password}")
    private String companyPassword;

    // ---------------------------------------------------------------
    // 1) 기본 DataSource — 기존 레포지터리 전체가 사용 (@Primary)
    //    Spring Boot 자동설정이 application.properties 를 읽어서
    //    이미 만들어주므로, 이 빈을 별도로 선언할 필요는 없다.
    //    만약 기존 자동설정과 충돌이 생기면 @Primary DataSource 빈을
    //    아래처럼 명시적으로 선언한다.
    // ---------------------------------------------------------------
    // @Bean
    // @Primary
    // public DataSource defaultDataSource() { ... }

    // ---------------------------------------------------------------
    // 2) 기업용 DataSource — company_user 계정
    //    TODO: DriverManagerDataSource 를 생성하여
    //          companyUrl / companyUsername / companyPassword 를 세팅하고
    //          "companyDataSource" 라는 이름의 빈으로 반환
    // ---------------------------------------------------------------
    @Bean
    public DataSource companyDataSource() {
        // TODO: DriverManagerDataSource 생성
        // DriverManagerDataSource ds = new DriverManagerDataSource();
        // ds.setDriverClassName("org.mariadb.jdbc.Driver");
        // ds.setUrl(companyUrl);
        // ds.setUsername(companyUsername);
        // ds.setPassword(companyPassword);
        // return ds;
        throw new UnsupportedOperationException("TODO: companyDataSource 구현");
    }

    // ---------------------------------------------------------------
    // 3) 기업용 JdbcTemplate — companyDataSource 를 감싸는 템플릿
    //    CompanyRequestRepository 에서 @Qualifier("companyJdbcTemplate")
    //    로 주입받아 UPDATE status 쿼리를 실행한다
    // ---------------------------------------------------------------
    @Bean
    public JdbcTemplate companyJdbcTemplate() {
        // TODO: new JdbcTemplate(companyDataSource()) 반환
        throw new UnsupportedOperationException("TODO: companyJdbcTemplate 구현");
    }
}
