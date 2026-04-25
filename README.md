# OTP Email Verification System

A Spring Boot application to send and verify OTP via Gmail.

## 🚀 Features
- Send OTP to email
- Verify OTP (valid for 5 minutes)
- Simple frontend UI

## 🛠️ Tech Stack
- Spring Boot
- Java Mail Sender
- HTML, CSS, JavaScript

## ⚙️ Setup
1. Add Gmail credentials in `application.properties`
2. Run the project
3. Open: http://localhost:8080

## 🔌 APIs
- Send OTP: `POST /api/otp/send?email=your-email`
- Verify OTP: `POST /api/otp/verify?email=your-email&otp=123456`

## 👨‍💻 Author
Juned Bankapur
