package com.hospital.queue.Entities;

import jakarta.persistence.OneToOne;

public class Slot {
    private int day;
    private int hour;
    private Long patientId;
}
