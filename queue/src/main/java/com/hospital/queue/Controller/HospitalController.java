package com.hospital.queue.Controller;

import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Hospital;
import com.hospital.queue.Service.Implementations.HospitalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
@CrossOrigin(origins = "http://localhost:5173")
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @PostMapping("/add")
    public Hospital addHospital(@RequestBody Hospital hospital){
        hospitalService.addHospital(hospital);
        return hospital;
    }
    @GetMapping("/get/{input}")
    public List<Hospital> getHospital(@PathVariable("input") String input){
        return hospitalService.getHospitalWith(input);
    }
    @PostMapping("/doctors")
    public List<?> getAllDoctors(@RequestBody LoginRequest  loginRequest){
        return hospitalService.getAllDoctors(loginRequest);
    }
}
