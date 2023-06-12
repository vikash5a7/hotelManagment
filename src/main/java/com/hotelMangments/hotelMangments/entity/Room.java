package com.hotelMangments.hotelMangments.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotelMangments.hotelMangments.enums.RoomType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    private Integer capacity;
    private BigDecimal price;
    @ElementCollection
    private Set<String> amenities;
    private boolean isOccupied = false;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

}
