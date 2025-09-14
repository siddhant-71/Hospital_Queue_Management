package com.hospital.queue.Exception;

public class PaymentFailed extends RuntimeException {
    public PaymentFailed(String message) {
        super("Payment Failed");
    }
}
