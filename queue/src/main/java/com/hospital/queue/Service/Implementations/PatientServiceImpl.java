package com.hospital.queue.Service.Implementations;

import com.hospital.queue.DTO.LoginRequest;
import com.hospital.queue.DTO.PatientDTO;
import com.hospital.queue.Entities.OtpEntity;
import com.hospital.queue.Entities.Patient;
import com.hospital.queue.Repository.OtpRepository;
import com.hospital.queue.Repository.PatientRepository;
import com.hospital.queue.Service.Interface.PatientService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private OtpRepository otpRepository;

    @Override
    public PatientDTO registerPatient(Patient patient) {
        patientRepository.save(patient);
        return PatientToDTO(patient);
    }

    @Override
    public PatientDTO loginPatient(LoginRequest request) {
        String input=request.getInput();
        String password=request.getPassword();
        Patient patient=patientRepository.findByEmail(input);
        if(patient!=null && patient.getPassword().equals(password)){
            return PatientToDTO(patient);
        }
        patient=patientRepository.findByPhoneNumber(input);
        if(patient!=null && patient.getPassword().equals(password)){
            return PatientToDTO(patient);
        }
        throw new RuntimeException("Invalid credentials");
    }
    private String generateOtp(){
        Random random=new Random();
        StringBuilder otp=new StringBuilder();
        for(int i=0;i<6;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
    private void sendActual(String otp,String input) throws Exception {
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
        Patient patient=patientRepository.findByPhoneNumber(input);
        if(patient==null){
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PatientDTO loginPatientViaOtp(LoginRequest request) {
        String input=request.getInput();
        String Otp= request.getPassword();
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
        if(otpEntity.getOtp().equals(Otp)){
            otpRepository.delete(otpEntity);
        }
        return PatientToDTO(patientRepository.findByPhoneNumber(input));
    }

    private PatientDTO PatientToDTO(Patient patient) {
        PatientDTO ans = new PatientDTO();

        ans.setAge(patient.getAge());
        ans.setBloodGroup(patient.getBloodGroup());
        ans.setEmail(patient.getEmail());
        ans.setGender(patient.getGender());
        ans.setName(patient.getName());
        ans.setPhoneNumber(patient.getPhoneNumber());
        ans.setId(patient.getId());
        return ans;
    }
}
