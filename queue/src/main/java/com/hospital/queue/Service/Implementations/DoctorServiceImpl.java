package com.hospital.queue.Service.Implementations;


import com.hospital.queue.DTO.DoctorDTO;
import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.Entities.Doctor;
import com.hospital.queue.Entities.OtpEntity;
import com.hospital.queue.Repository.DoctorRepository;
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
    private OtpRepository otpRepository;

    @Override
    public DoctorDTO registerDoctor(Doctor doctor) {
        List<Long> slot=new ArrayList<>();
        for(int i=0;i<56;i++){
            slot.add(0L);
        }
        doctor.setSlots(slot);
        doctorRepository.save(doctor);
        return DoctorToDoctorDTO(doctor);
    }

    @Override
    public DoctorDTO loginDoctor(LoginRequest request) {
        String input=request.getInput();
        String password=request.getPassword();
        Doctor doctor=doctorRepository.findByEmail(input);
        if(doctor==null){
            doctor=doctorRepository.findByPhoneNumber(input);
            if(doctor==null){
                throw new RuntimeException("User Not Found With this input To login ");
            }
        }
        if(doctor.getPassword().equals(password)){
            return DoctorToDoctorDTO(doctor);
        }
        throw new RuntimeException("Invalid credentials");
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
            throw new RuntimeException("User Not Found With this input To send the OTP ");
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
            throw new RuntimeException("Invalid OTP or otpEntity entry deleted ");
        }
        if(otpEntity.isUsed()){
            throw new RuntimeException("OTP Already Used");
        }
        if(otpEntity.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("OTP Expired Use Resend Otp again");
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
