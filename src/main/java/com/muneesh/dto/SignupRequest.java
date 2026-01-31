package com.muneesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
//@NoArgsConstructor
//@AllArgsConstructor

public class SignupRequest {
    @NotBlank(message = "First name is Required")
    private String firstname;
    @NotBlank(message = "First name is Required")
    private String lastname;
    @NotBlank(message = "Role is Required")
    private String role;
    @Email(message = "email should be in valid form")
    private String UserName;
    @Size(min = 6, max = 10, message = "password must be between 6 and 10 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
            message = "password must contain one Uppercase letter and one digit")
    private String password;
    private String email;

}