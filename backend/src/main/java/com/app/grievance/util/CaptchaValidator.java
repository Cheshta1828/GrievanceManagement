package com.app.grievance.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class CaptchaValidator {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6LddIVIrAAAAALb9XXiSgoTBIlA_r7RcPKP72Sut";
    private static final Logger logger = LoggerFactory.getLogger(CaptchaValidator.class);

    public boolean verifyCaptcha(String captchaToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("secret", SECRET_KEY);
        formData.add("response", captchaToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(RECAPTCHA_VERIFY_URL, request, Map.class);
            Map<String, Object> responseBody = responseEntity.getBody();
            logger.info("CAPTCHA verification response: {}", responseBody);

            return responseBody != null && Boolean.TRUE.equals(responseBody.get("success"));
        } catch (Exception e) {
            logger.error("CAPTCHA verification failed due to error", e);
            return false;
        }
    }
}

