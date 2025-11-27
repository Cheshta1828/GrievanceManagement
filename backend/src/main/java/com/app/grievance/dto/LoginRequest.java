package com.app.grievance.dto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private String role;
    private String captchaToken;
}
