package com.hotelMangments.hotelMangments.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequest {
    private Long roomId;
    private Guest guest;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Payment payment;
}
