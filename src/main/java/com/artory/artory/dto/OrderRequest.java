package com.artory.artory.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;

    private List<Long> productIds;
}
