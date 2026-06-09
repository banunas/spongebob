package spongebob.sponsors.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SponsorshipRequest {
    private Integer requestId;
    private Long eventId;
    private Long companyId;
    private String status;
    private Integer quantity;
    private LocalDateTime createdAt;
}


