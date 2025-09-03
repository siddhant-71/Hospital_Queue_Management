package com.hospital.queue.Service.Implementations;

import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.Hospital;
import com.hospital.queue.Repository.DoctorRepository;
import com.hospital.queue.Repository.HospitalRepository;
import com.hospital.queue.Service.Interface.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public Hospital addHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
        return hospital;
    }

    @Override
    public List<Doctor> getAllDoctors(Hospital hospital) {
        return doctorRepository.findAllByHospitalId(hospital.getId());
    }
}
