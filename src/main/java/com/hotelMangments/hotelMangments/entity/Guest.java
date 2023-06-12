package com.hotelMangments.hotelMangments.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "guests")
@Data
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}

