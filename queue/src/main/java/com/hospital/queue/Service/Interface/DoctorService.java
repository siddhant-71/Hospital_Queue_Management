package com.hospital.queue.Service.Interface;

import com.hospital.queue.DTO.DoctorDTO;
import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Doctor;

public interface DoctorService {
    DoctorDTO registerDoctor(Doctor doctor);
    DoctorDTO loginDoctor(LoginRequest request);
    boolean sendOtp(String input);
    DoctorDTO loginDoctorViaOtp(LoginRequest request);
}
