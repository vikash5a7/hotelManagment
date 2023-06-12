package com.hotelMangments.hotelMangments.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
