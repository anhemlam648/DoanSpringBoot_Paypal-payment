package com.example.DoAnJava.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PayPalOder {
    private List<Long>productId;
    private BigDecimal price;
    private String currency;
    private String method;
    private String intent;
    private String description;

}