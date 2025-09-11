package com.hospital.queue.Exception;

public class DoctorNotFound extends RuntimeException {
    public DoctorNotFound(String message) {
        super("Doctor Not Found");
    }
}
