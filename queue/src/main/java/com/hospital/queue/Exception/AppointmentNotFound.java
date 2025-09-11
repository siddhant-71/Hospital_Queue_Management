package com.hospital.queue.Exception;

public class AppointmentNotFound extends RuntimeException {
    public AppointmentNotFound(String message) {
        super("Appointment not found:");
    }
}
