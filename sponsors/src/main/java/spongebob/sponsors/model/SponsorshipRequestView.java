package spongebob.sponsors.model;

import lombok.Data;
import java.time.LocalDateTime;

// 협찬요청 목록 화면용 (행사명·기업명 JOIN 결과)
@Data
public class SponsorshipRequestView {
    private int requestId;
    private int eventId;
    private String eventName;
    private int companyId;
    private String companyName;
    private String status;
    private int quantity;
    private LocalDateTime createdAt;
}
