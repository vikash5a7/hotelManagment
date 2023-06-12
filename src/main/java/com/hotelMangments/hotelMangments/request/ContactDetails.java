package com.hotelMangments.hotelMangments.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDetails {
    private String phone;
    private String email;
    private Address address;
}
