package com.hospital.queue.Controller;

import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.DTO.PatientDTO;
import com.hospital.queue.Entities.Patient;
import com.hospital.queue.Service.Implementations.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientService;

    @PostMapping("/login")
    public PatientDTO loginPatient(@RequestBody LoginRequest loginRequest){
        return patientService.loginPatient(loginRequest);
    }
    @PostMapping("/sendOtp/{input}")
    public boolean sendOtp(@PathVariable("input") String input){
        try{
            patientService.sendOtp(input);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/loginViaOtp")
    public PatientDTO loginPatientViaOtp(@RequestBody LoginRequest loginRequest){
        return patientService.loginPatientViaOtp(loginRequest);
    }
    @PostMapping("/register")
    public PatientDTO registerPatient(@RequestBody Patient patient){
        return patientService.registerPatient(patient);
    }
}
