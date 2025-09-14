package com.hospital.queue.Controller;

import com.hospital.queue.DTO.AppointmentBookedResponse;
import com.hospital.queue.DTO.AppointmentDTO;
import com.hospital.queue.DTO.PatientAndDoctor;
import com.hospital.queue.Entities.Appointment;
import com.hospital.queue.Repository.AppointmentRepository;
import com.hospital.queue.Service.Implementations.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("/get/{id}")
    public AppointmentDTO getAppointment(@PathVariable("id")Long id){
        return appointmentService.getAppointment(id);
    }
    @PostMapping("/book/{slot}")
    public AppointmentBookedResponse bookAppointment(@RequestBody PatientAndDoctor p, @PathVariable("slot") int slot){
        return appointmentService.bookAppointment(p.getDoctorId(),p.getPatientId(),slot);
    }
    @PostMapping("/cancel/{slot}/{id}")
    public boolean cancelAppointment(@RequestBody PatientAndDoctor p, @PathVariable("slot") int slot,@PathVariable("id") Long id){
        return appointmentService.cancelAppointment(p.getDoctorId(),p.getPatientId(),slot,id,0);
    }
    @PostMapping("/reject/{slot}/{id}")
    public boolean rejectAppointment(@RequestBody PatientAndDoctor p,@PathVariable("slot") int slot,@PathVariable("id") Long id){
        return appointmentService.cancelAppointment(p.getDoctorId(),p.getPatientId(),slot,id,1);
    }
    @PostMapping("/done/{slot}/{id}")
    public boolean doneAppointment(@RequestBody PatientAndDoctor p,@PathVariable("slot") int slot,@PathVariable("id") Long id){
        return appointmentService.cancelAppointment(p.getDoctorId(),p.getPatientId(),slot,id,2);
    }
}
