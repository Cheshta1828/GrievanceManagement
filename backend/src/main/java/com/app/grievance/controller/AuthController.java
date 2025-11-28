package com.app.grievance.controller;
import com.app.grievance.dto.LoginRequest;
import com.app.grievance.dto.LoginResponse;
import com.app.grievance.dto.RegisterRequest;
import com.app.grievance.dto.RegisterResponse;
import com.app.grievance.exception.CustomException;
import com.app.grievance.service.AuthService;
import com.app.grievance.util.CaptchaValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CaptchaValidator captchaValidator;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.info("Login request received for username: {}", request.getEmail());
        logger.info("CAPTCHA token received in backend: {}", request.getCaptchaToken());

        // ⛔ Check CAPTCHA before continuing
        if (!captchaValidator.verifyCaptcha(request.getCaptchaToken())) {
            logger.warn("CAPTCHA verification failed for user: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "CAPTCHA verification failed. Please try again."));
        }

        try {
            LoginResponse response = authService.login(request);
            logger.info("Login successful for username: {}, returning response: {}", request.getEmail(), response);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            logger.warn("Custom error during login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error occurred during login for username: {}", request.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An unexpected error occurred during login"));
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.register(request);
            // Always return a JSON object with a 'message' property for success
            // Wrap the response in a map for consistency
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("message", response.getMessage()));
        } catch (CustomException e) {
            String msg = e.getMessage();
            // Always return a JSON object with a 'message' property for errors
            if (msg != null && (msg.toLowerCase().contains("already exists") || msg.toLowerCase().contains("duplicate") || msg.toLowerCase().contains("taken"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", "This email is already registered. Please use a different email or login."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", msg != null ? msg : "Registration failed. Please try again."));
        } catch (Exception e) {
            logger.error("Unexpected error during registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An unexpected error occurred during registration"));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test endpoint");
    }
}
