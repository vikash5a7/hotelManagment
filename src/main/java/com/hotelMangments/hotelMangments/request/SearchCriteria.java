package com.hotelMangments.hotelMangments.request;

import lombok.Data;

import java.util.Set;

@Data
public class SearchCriteria {
    private String location;
    private String name;
    private Set<String> amenities;
}

