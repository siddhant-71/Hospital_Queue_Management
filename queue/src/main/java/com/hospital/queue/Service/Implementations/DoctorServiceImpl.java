package com.hospital.queue.Service.Implementations;


import com.hospital.queue.DTO.DoctorDTO;
import com.hospital.queue.DTO.DoctorRegister;
import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Appointment;
import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.Hospital;
import com.hospital.queue.Entities.OtpEntity;
import com.hospital.queue.Exception.*;
import com.hospital.queue.Repository.DoctorRepository;
import com.hospital.queue.Repository.HospitalRepository;
import com.hospital.queue.Repository.OtpRepository;
import com.hospital.queue.Service.Interface.DoctorService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private OtpRepository otpRepository;

    public boolean deleteDoctor(Long id){
        try {
            doctorRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    @Override
    public DoctorDTO registerDoctor(DoctorRegister doctor) {
        Hospital hospital=hospitalRepository.findById(doctor.getHospitalId()).orElseThrow(()->new HospitalNotFound(doctor.getHospitalId().toString()));
        Doctor de=doctorRepository.findByPhoneNumber(doctor.getPhoneNumber());
        if(de!=null){
            throw new RuntimeException("User Already Registered With this Phone Number");
        }
        Doctor d=new Doctor();
        d.setName(doctor.getName());
        d.setSpecialization(doctor.getSpecialization());
        d.setHospital(hospital);
        d.setPhoneNumber(doctor.getPhoneNumber());
        d.setAge(doctor.getAge());
        d.setArea(doctor.getArea());
        d.setCity(doctor.getCity());
        d.setCountry(doctor.getCountry());
        d.setDegrees(doctor.getDegrees());
        d.setDepartment(doctor.getDepartment());
        d.setEmail(doctor.getEmail());
        d.setPassword(doctor.getPassword());
        d.setGender(doctor.getGender());
        d.setState(doctor.getStreet());
        d.setState(doctor.getState());
        d.setCountry(doctor.getCountry());
        d.setPincode(doctor.getPincode());
        d.setAppointment(new ArrayList<>());
        List<Long> slot=new ArrayList<>();
        for(int i=0;i<56;i++){
            slot.add(0L);
        }
        d.setSlots(slot);
        doctorRepository.save(d);
        return DoctorToDoctorDTO(d);
    }

    @Override
    public DoctorDTO loginDoctor(LoginRequest request) {
        String input=request.getInput();
        String password=request.getPassword();
        Doctor doctor=doctorRepository.findByEmail(input);
        if(doctor==null){
            doctor=doctorRepository.findByPhoneNumber(input);
            if(doctor==null){
                throw new DoctorNotFound("Doctor Not Found With this input");
            }
        }
        if(doctor.getPassword().equals(password)){
            return DoctorToDoctorDTO(doctor);
        }
        throw new WrongCredentials("Wrong credentials entered");
    }
    private String generateOtp(){
        StringBuilder otp=new StringBuilder();
        for(int i=0;i<6;i++){
            otp.append((int)(Math.random()*10));
        }
        return otp.toString();
    }
    private void sendActual(String otp,String input){
        final String accountTwilio="AC89032888d9ecbaf8cff88ad5d273232b";
        final String AuthKey="eb5c3278fa08efac87bcff4d60d6230b";
        Twilio.init(accountTwilio,AuthKey);
        Message message=Message.creator(new PhoneNumber("+91"+input),
                        new PhoneNumber("+19313481878"),
                        "This is your Otp for Login "+ otp +" and this is valid for 5 minutes")
                .create();
        System.out.println(otp + " Sending OTP to "+input);
    }
    @Override
    public boolean sendOtp(String input) {
        Doctor doctor=doctorRepository.findByPhoneNumber(input);
        if(doctor==null){
            throw new DoctorNotFound("Doctor Not Found With this input");
        }
        OtpEntity otpEntity=otpRepository.findByInput(input);
        if(otpEntity==null){
            otpEntity=new OtpEntity();
        }
        otpEntity.setUsed(false);
        otpEntity.setInput(input);
        otpEntity.setOtpTime(LocalDateTime.now());
        otpEntity.setOtp(generateOtp());
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);
        try{
            sendActual(otpEntity.getOtp(),otpEntity.getInput());
            return true;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoctorDTO loginDoctorViaOtp(LoginRequest request) {
        String input=request.getInput();
        String otp=request.getPassword();
        OtpEntity otpEntity=otpRepository.findByInput(input);
        if(otpEntity==null){
            throw new WrongCredentials("Invalid OTP or otpEntity entry deleted ");
        }
        if(otpEntity.isUsed()){
            throw new OtpUsed("Used");
        }
        if(otpEntity.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new OtpExpired("Expired");
        }
        if(otpEntity.getOtp().equals(otp)){
            otpRepository.delete(otpEntity);
        }
        return DoctorToDoctorDTO(doctorRepository.findByPhoneNumber(input));
    }

    private DoctorDTO DoctorToDoctorDTO(Doctor doctor){
        DoctorDTO ans=new DoctorDTO();
        ans.setAge(doctor.getAge());
        ans.setDegrees(doctor.getDegrees());
        ans.setDepartment(doctor.getDepartment());
        ans.setEmail(doctor.getEmail());
        ans.setGender(doctor.getGender());
        ans.setHospital(doctor.getHospital());
        ans.setName(doctor.getName());
        ans.setPhoneNumber(doctor.getPhoneNumber());
        ans.setSpecialization(doctor.getSpecialization());
        ans.setSlots(doctor.getSlots());
        ans.setAppointment(doctor.getAppointment());
        ans.setStreet(doctor.getStreet());
        ans.setArea(doctor.getArea());
        ans.setCity(doctor.getCity());
        ans.setState(doctor.getState());
        ans.setCountry(doctor.getCountry());
        ans.setPincode(doctor.getPincode());
        ans.setId(doctor.getId());
        return ans;
    }
}
