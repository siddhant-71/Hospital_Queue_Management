package com.hospital.queue.Service.Interface;

import com.hospital.queue.DTO.RazorpayResponseDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.util.Map;

public interface PaymentService {
    public Map<String,String> initiatePayment(Long appointmentId) throws Exception;
    public Map<String,String> completePayment(RazorpayResponseDTO dto, Long appId) throws Exception;
}
