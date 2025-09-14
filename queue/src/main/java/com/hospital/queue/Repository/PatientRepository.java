package com.hospital.queue.Repository;

import com.hospital.queue.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);
    Patient findByPhoneNumber(String phoneNumber);
}
