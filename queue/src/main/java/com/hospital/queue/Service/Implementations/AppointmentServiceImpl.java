package com.hospital.queue.Service.Implementations;

import com.hospital.queue.DTO.AppointmentBookedResponse;
import com.hospital.queue.DTO.AppointmentDTO;
import com.hospital.queue.Entities.*;
import com.hospital.queue.Exception.AppointmentNotFound;
import com.hospital.queue.Exception.DoctorNotFound;
import com.hospital.queue.Exception.PatientNotFound;
import com.hospital.queue.Repository.AppointmentRepository;
import com.hospital.queue.Repository.DoctorRepository;
import com.hospital.queue.Repository.PatientRepository;
import com.hospital.queue.Service.Interface.AppointmentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
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
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()->new DoctorNotFound(doctorId.toString()));
        Patient patient=patientRepository.findById(patientId).orElseThrow(()->new PatientNotFound(patientId.toString()));
        Appointment appointment=new Appointment();
        appointment.setBookingTime(LocalDateTime.now());
        appointment.setDoctor(doctor);
        appointment.setStatus(AppointmentStatus.valueOf("PENDING"));
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        List<Long> slots=new ArrayList<>();
        for(int i=0;i<56;i++){
            if(i==slot){
                slots.add(appointment.getId());
                continue;
            }
            slots.add(doctor.getSlots().get(i));
        }
        doctor.setSlots(slots);
        doctorRepository.save(doctor);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
        try {
            initiatePayment(appointment.getId());//Check when to initiate the payment
        }
        catch (RazorpayException e){
            System.out.println(e.getMessage());
            appointment.setStatus(AppointmentStatus.valueOf("REJECTED"));
            appointmentRepository.save(appointment);
            doctorRepository.save(doctor);
        }
        return createResponse(appointment,slot,doctor.getName(),patient.getName()) ;
    }
    public Order initiatePayment(Long appointmentId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_RDFwXdw2vLKmBJ", "IfvgyaZtdUp914tdSlnNGMBB");
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",10000);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","appointment"+appointmentId);
        JSONObject notes=new JSONObject();
        notes.put("appointmentId",appointmentId.toString());
        orderRequest.put("notes",notes);
        Order order = razorpay.orders.create(orderRequest);
        return order;
    }
    @Override
    public boolean cancelAppointment(Long doctorId, Long patientId, int slot,Long id,int isReject) {
        try {
            Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()->new DoctorNotFound(doctorId.toString()));
            Patient patient=patientRepository.findById(patientId).orElseThrow(()->new PatientNotFound(patientId.toString()));
            List<Long> slots=new ArrayList<>();
            for(int i=0;i<56;i++){
                if(i==slot){
                    slots.add(0L);
                    continue;
                }
                slots.add(doctor.getSlots().get(i));
            }
            doctor.setSlots(slots);
            Appointment appointment=appointmentRepository.findById(id).orElseThrow(()->new AppointmentNotFound(id.toString()));
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
    public AppointmentDTO getAppointment(Long id){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow(()->new AppointmentNotFound(id.toString()));
        AppointmentDTO ans=new AppointmentDTO();
        ans.setAppointmentId(appointment.getId());
        ans.setDoctorId(appointment.getDoctor().getId());
        ans.setPatientId(appointment.getPatient().getId());
        ans.setStatus(appointment.getStatus().toString());
        ans.setBookingTime(appointment.getBookingTime());
        ans.setSlot(appointment.getDoctor().getSlots().indexOf(appointment.getId()));
        return ans;
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
