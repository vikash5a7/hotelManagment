package com.hotelMangments.hotelMangments.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class Hotel {
    private String name;
    private String location;
    private String description;
    private String photos;
    private Set<String> amenities;
    private ContactDetails contactDetails;
}
