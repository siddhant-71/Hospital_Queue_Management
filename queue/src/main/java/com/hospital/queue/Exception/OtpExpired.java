package com.hospital.queue.Exception;

public class OtpExpired extends RuntimeException {
    public OtpExpired(String message) {
        super("Otp Expired");
    }
}
