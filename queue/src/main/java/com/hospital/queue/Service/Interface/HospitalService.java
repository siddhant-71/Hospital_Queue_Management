package com.hospital.queue.Service.Interface;


import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.Hospital;

import java.util.List;

public interface HospitalService {
    Hospital addHospital(Hospital hospital);
    List<Doctor> getAllDoctors(LoginRequest loginRequest);
}
