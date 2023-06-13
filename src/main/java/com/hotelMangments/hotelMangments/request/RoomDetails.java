package com.hotelMangments.hotelMangments.request;

import com.hotelMangments.hotelMangments.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class RoomDetails {
    private String roomNumber;
    private RoomType roomType;
    private Integer capacity;
    private BigDecimal price;
    private Set<String> amenities;

    private boolean isOccupied = false;
}
