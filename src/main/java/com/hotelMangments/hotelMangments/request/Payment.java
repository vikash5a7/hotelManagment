package com.hotelMangments.hotelMangments.request;

import com.hotelMangments.hotelMangments.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Payment {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentId;
    private PaymentStatus paymentStatus;
}
