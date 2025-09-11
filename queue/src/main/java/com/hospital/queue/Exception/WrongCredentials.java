package com.hospital.queue.Exception;

public class WrongCredentials extends RuntimeException {
    public WrongCredentials(String message) {
        super("Invalid Credentials");
    }
}
