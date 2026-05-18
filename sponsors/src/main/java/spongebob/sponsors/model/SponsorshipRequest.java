package spongebob.sponsors.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SponsorshipRequest {
    private int requestId;
    private int eventId;
    private int companyId;
    private String status;
    private int quantity;
    private LocalDateTime createdAt;
}
