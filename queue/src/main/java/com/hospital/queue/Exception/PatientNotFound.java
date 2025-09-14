package com.hospital.queue.Exception;

public class PatientNotFound extends RuntimeException{
    public PatientNotFound(String message) {
        super("Patient not found:");
    }
}
