package com.hospital.queue.Controller;

import com.hospital.queue.DTO.DoctorDTO;
import com.hospital.queue.DTO.DoctorRegister;
import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.Hospital;
import com.hospital.queue.Repository.HospitalRepository;
import com.hospital.queue.Service.Implementations.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {
    @Autowired
    private DoctorServiceImpl doctorService;
    @Autowired
    private HospitalRepository hospitalRepository;
    @PostMapping("/hospital")
    public void addHospital(@RequestBody Hospital hospital){
        hospitalRepository.save(hospital);
    }
    @PostMapping("/login")
    public DoctorDTO loginDoctor(@RequestBody LoginRequest loginRequest){
        return doctorService.loginDoctor(loginRequest);
    }
    @PostMapping("/sendOtp/{input}")
    public boolean sendOtp(@PathVariable("input") String input){
        try{
            doctorService.sendOtp(input);
            return true;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/loginViaOtp")
    public DoctorDTO loginDoctorViaOtp(@RequestBody LoginRequest loginRequest){
        return doctorService.loginDoctorViaOtp(loginRequest);
    }

    @PostMapping("/delete/{id}")
    public boolean deleteDoctor(@PathVariable("id") Long id){
        return doctorService.deleteDoctor(id);
    }
    @PostMapping("/register")
    public DoctorDTO registerDoctor(@RequestBody DoctorRegister doctor){
        return doctorService.registerDoctor(doctor);
    }
}
