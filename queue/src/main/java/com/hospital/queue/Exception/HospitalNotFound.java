package com.hospital.queue.Exception;

public class HospitalNotFound extends RuntimeException {
    public HospitalNotFound(String message) {
        super("Hospital not found:");
    }
}
