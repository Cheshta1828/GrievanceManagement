package com.app.grievance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;    // The authentication token (JWT)
    private String username; // Username of the logged-in user
    private String email;    // Email of the logged-in user
    private String role;
}