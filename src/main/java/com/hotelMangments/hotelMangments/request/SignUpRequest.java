package com.hotelMangments.hotelMangments.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotEmpty
    @Size(min = 2, message = "First name should have at least 2 characters")
    private String firstName;

    @NotEmpty
    @Size(min = 2, message = "Last name should have at least 2 characters")
    private String lastName;

    @NotEmpty
    @Email(message = "invalid email")
    private String email;

    @NotEmpty(message = "password can't be null")
    private String password;

}
