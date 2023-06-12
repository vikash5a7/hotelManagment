package com.hotelMangments.hotelMangments.request;

import com.hotelMangments.hotelMangments.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
