package com.hospital.queue.Controller;

import com.hospital.queue.Entities.Hospital;
import com.hospital.queue.Service.Implementations.HospitalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @PostMapping("/add")
    public Hospital addHospital(Hospital hospital){
        hospitalService.addHospital(hospital);
        return hospital;
    }

    @PostMapping("/doctors")
    public List<?> getAllDoctors(@RequestBody Hospital hospital){
        return hospitalService.getAllDoctors(hospital);
    }
}
