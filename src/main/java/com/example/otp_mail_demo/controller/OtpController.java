package com.example.otp_mail_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.otp_mail_demo.service.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // ✅ SEND OTP
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        otpService.sendOtp(email);
        return ResponseEntity.ok("OTP sent successfully to " + email);
    }

    // ✅ ADD THIS METHOD HERE
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email,
                                            @RequestParam String otp) {

        boolean isValid = otpService.verifyOtp(email, otp);

        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully ✅");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP ❌");
        }
    }
}