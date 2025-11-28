package com.app.grievance.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;
}
