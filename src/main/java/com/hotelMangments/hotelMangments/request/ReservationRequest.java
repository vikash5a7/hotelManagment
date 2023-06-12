package com.hotelMangments.hotelMangments.request;

import com.hotelMangments.hotelMangments.entity.Guest;
import com.hotelMangments.hotelMangments.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequest {
    private Long roomId;
    private Guest guest;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
}
