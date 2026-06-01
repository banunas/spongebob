package spongebob.sponsors.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.Company;

import java.util.List;

@Repository
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompanyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Company> findAll() {
        String sql = "SELECT company_id, company_name, product_name, contact FROM company ORDER BY company_name";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }

    public Company findByPassword(String password) {
        String sql = "SELECT company_id, company_name, product_name, contact FROM company WHERE password = ?";
        List<Company> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Company.class), password);
        return list.isEmpty() ? null : list.get(0);
    }
}
