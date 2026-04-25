package com.example.otp_mail_demo.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final JavaMailSender javaMailSender;
    private final SecureRandom secureRandom = new SecureRandom();

    // Store OTP with expiry
    private final Map<String, OtpData> otpStorage = new ConcurrentHashMap<>();

    @Value("${spring.mail.username}")
    private String fromEmail;

    public OtpService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // ===================== SEND OTP =====================
    public void sendOtp(String toEmail) {
        try {
            String otp = generateOtp();

            // store OTP with expiry (5 mins)
            otpStorage.put(
                    toEmail,
                    new OtpData(otp, LocalDateTime.now().plusMinutes(5))
            );

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText(
                    "Hello,\n\n" +
                    "Your OTP is: " + otp + "\n\n" +
                    "This OTP is valid for 5 minutes.\n\n" +
                    "Thank you."
            );

            javaMailSender.send(message);

            System.out.println("OTP sent to: " + toEmail);
            System.out.println("Generated OTP: " + otp);

        } catch (Exception e) {
            System.out.println("ERROR SENDING EMAIL:");
            e.printStackTrace();
        }
    }

    // ===================== VERIFY OTP =====================
    public boolean verifyOtp(String email, String enteredOtp) {

        OtpData otpData = otpStorage.get(email);

        if (otpData == null) {
            return false;
        }

        // check expiry
        if (otpData.expiryTime().isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            return false;
        }

        // check OTP match
        if (otpData.otp().equals(enteredOtp)) {
            otpStorage.remove(email); // remove after success
            return true;
        }

        return false;
    }

    // ===================== GENERATE OTP =====================
    private String generateOtp() {
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }

    // ===================== RECORD =====================
    private record OtpData(String otp, LocalDateTime expiryTime) {
    }
}