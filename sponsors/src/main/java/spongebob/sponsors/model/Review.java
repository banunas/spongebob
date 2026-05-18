package spongebob.sponsors.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Review {
    private int reviewId;
    private int requestId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
