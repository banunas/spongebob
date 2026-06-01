package spongebob.sponsors.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spongebob.sponsors.model.Review;

// 담당: 서아
@Repository
public class ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 후기 등록
    public void save(Review review) {
        
        String sql = """
            INSERT INTO review (request_id, rating, comment, created_at) 
            VALUES (?, ?, ?, NOW());
            """;
        jdbcTemplate.update(sql, review.getRequestId(), review.getRating(), review.getComment());
    }
}
