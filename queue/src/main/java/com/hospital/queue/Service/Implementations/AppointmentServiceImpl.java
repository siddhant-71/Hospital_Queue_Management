package com.hospital.queue.Service.Implementations;

import com.hospital.queue.DTO.AppointmentBookedResponse;
import com.hospital.queue.Entities.Appointment;
import com.hospital.queue.Entities.AppointmentStatus;
import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.Patient;
import com.hospital.queue.Repository.AppointmentRepository;
import com.hospital.queue.Repository.DoctorRepository;
import com.hospital.queue.Repository.PatientRepository;
import com.hospital.queue.Service.Interface.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Override
    public AppointmentBookedResponse bookAppointment(Long doctorId, Long patientId, int slot) {
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()->new RuntimeException("Doctor Not Found"));
        Patient patient=patientRepository.findById(patientId).orElseThrow(()->new RuntimeException("Patient Not Found"));
        List<Long> slots=new ArrayList<>();
        for(int i=0;i<56;i++){
            if(i==slot){
                slots.add(patientId);
                continue;
            }
            slots.add(doctor.getSlots().get(i));
        }
        doctor.setSlots(slots);
        doctorRepository.save(doctor);

        Appointment appointment=new Appointment();
        appointment.setBookingTime(LocalDateTime.now());
        appointment.setDoctor(doctor);
        appointment.setStatus(AppointmentStatus.valueOf("CONFIRMED"));
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        //return appointment;
        return createResponse(appointment,slot,doctor.getName(),patient.getName()) ;
    }

    @Override
    public boolean cancelAppointment(Long doctorId, Long patientId, int slot,Long id,int isReject) {
        try {
            Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()->new RuntimeException("Doctor Not Found"));
            Patient patient=patientRepository.findById(patientId).orElseThrow(()->new RuntimeException("Patient Not Found"));
            List<Long> slots=new ArrayList<>();
            for(int i=0;i<56;i++){
                if(i==slot){
                    slots.add(0L);
                    continue;
                }
                slots.add(doctor.getSlots().get(i));
            }
            doctor.setSlots(slots);
            Appointment appointment=appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Appointment Not Found"));
            if(isReject==0){
                appointment.setStatus(AppointmentStatus.valueOf("CANCELLED"));
            }
            else if(isReject==1){
                appointment.setStatus(AppointmentStatus.valueOf("REJECTED"));
            }
            else{
                appointment.setStatus(AppointmentStatus.valueOf("COMPLETED"));
            }
            appointmentRepository.save(appointment);
            doctorRepository.save(doctor);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private AppointmentBookedResponse createResponse(Appointment appointment,int slot,String doctorName,String patientName){
        AppointmentBookedResponse ans=new AppointmentBookedResponse();
        ans.setAppointmentId(appointment.getId());
        ans.setBookingTime(appointment.getBookingTime());
        ans.setStatus(appointment.getStatus().toString());
        ans.setSlot(slot);
        ans.setDoctorName(doctorName);
        ans.setPatientName(patientName);
        return ans;
    }
}
