package com.hospital.queue.Service.Implementations;

import com.hospital.queue.DTO.RazorpayResponseDTO;
import com.hospital.queue.Entities.Payment;
import com.hospital.queue.Entities.PaymentStatus;
import com.hospital.queue.Repository.PaymentRepository;
import com.hospital.queue.Service.Interface.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    public PaymentRepository paymentRepository;

    @Value("${razorpay.key.id}")
    private String Key;
    @Value("${razorpay.key.secret}")
    private String secretKey;

    @Override
    public Map<String, String> initiatePayment(Long appointmentId) throws RazorpayException {
        try {
            RazorpayClient razorpay = new RazorpayClient(Key,secretKey);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",10000);
            orderRequest.put("currency","INR");
            orderRequest.put("receipt","appointment"+appointmentId);
            JSONObject notes=new JSONObject();
            notes.put("appointmentId",appointmentId.toString());
            orderRequest.put("notes",notes);
            Order order=razorpay.orders.create(orderRequest);
            Map<String,String>response=new HashMap<>();
            response.put("razorpayOrderId",order.get("id"));
            response.put("currency",order.get("currency"));
            response.put("receipt",order.get("receipt"));
            response.put("amount",order.get("amount").toString());
            return response;
            //return order.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getTheSignature(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }
    @Override
    public Map<String, String> completePayment(RazorpayResponseDTO dto, Long appId) throws Exception {
        String paymentId=dto.getRazorpayPaymentID();
        String orderId=dto.getRazorpayOrderId();
        String signature= dto.getRazorpaySignature();
        System.out.println("one");
        Payment payment=new Payment();
        payment.setAppointment(appId);
        System.out.println("two");
        payment.setPaymentTime(LocalDateTime.now());
        payment.setAmount(100);
        payment.setRazorpayPaymentID(paymentId);
        payment.setPaymentStatus(PaymentStatus.valueOf("SUCCESS"));
        payment.setRazorpayOrderID(orderId);
        paymentRepository.save(payment);



        String generated_signature = getTheSignature(orderId + "|" + paymentId, secretKey);
        System.out.println("three");
        System.out.println(signature + " " + generated_signature);
        if (generated_signature.equals(signature)) {
            System.out.println(signature + " " + generated_signature);
            System.out.println("four");
            return Map.of("status", "failed", "appId", appId.toString(), "error", "Payment Failed. Please try again later.");
        }
        System.out.println("five");
        return Map.of("status", "success", "appId", appId.toString());
    }
}
