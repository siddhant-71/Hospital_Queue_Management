package com.hospital.queue.Exception;

public class OtpUsed extends RuntimeException {
    public OtpUsed(String message) {
        super("Otp Already Used");
    }
}
