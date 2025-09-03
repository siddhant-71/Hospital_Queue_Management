package com.hospital.queue.Service.Interface;

import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.DTO.PatientDTO;
import com.hospital.queue.Entities.Patient;

public interface PatientService {
    PatientDTO registerPatient(Patient patient);
    PatientDTO loginPatient(LoginRequest request);
    boolean sendOtp(String input);
    PatientDTO loginPatientViaOtp(LoginRequest request);
}
