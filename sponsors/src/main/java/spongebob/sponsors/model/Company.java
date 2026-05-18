package spongebob.sponsors.model;

import lombok.Data;

@Data
public class Company {
    private int companyId;
    private String companyName;
    private String productName;
    private String contact;
}
