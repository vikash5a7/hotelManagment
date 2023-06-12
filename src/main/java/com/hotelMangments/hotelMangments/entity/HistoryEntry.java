package com.hotelMangments.hotelMangments.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class HistoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    private String eventDetails;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public HistoryEntry(String eventType, String eventDetails) {
        this.eventType = eventType;
        this.eventDetails = eventDetails;
    }
}

