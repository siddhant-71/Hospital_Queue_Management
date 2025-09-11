package com.hospital.queue.Service.Implementations;

import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.Hospital;
import com.hospital.queue.Exception.HospitalNotFound;
import com.hospital.queue.Repository.DoctorRepository;
import com.hospital.queue.Repository.HospitalRepository;
import com.hospital.queue.Service.Interface.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public Hospital addHospital(@RequestBody Hospital hospital) {
        hospitalRepository.save(hospital);
        return hospital;
    }
    public List<Hospital> getHospitalWith(String input){
        return hospitalRepository.fuzzySearch(input);
    }
    @Override
    public List<Doctor> getAllDoctors(LoginRequest loginRequest) {
        String input=loginRequest.getInput();
        Hospital hp=hospitalRepository.findById(Long.valueOf(input)).orElseThrow(()->new HospitalNotFound("hospital Not Found"));
        return doctorRepository.findAllByHospitalId(Long.valueOf(input));
    }
}
