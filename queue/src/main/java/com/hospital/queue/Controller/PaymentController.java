package com.hospital.queue.Controller;

import com.hospital.queue.DTO.PaymentTemp;
import com.hospital.queue.DTO.RazorpayResponseDTO;
import com.hospital.queue.Service.Implementations.PaymentServiceImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping("/initiate/{appId}")
    public ResponseEntity<Map<String,String>> initiatePayment(@PathVariable("appId") Long appId) throws Exception {
        System.out.println("Step 1");
        return ResponseEntity.ok(paymentService.initiatePayment(appId));
    }
    @PostMapping("/verify")
    public boolean verifyPayment(@RequestBody PaymentTemp dto) throws RazorpayException {
        return true;
    }
    @PostMapping("/complete/{appId}")
    public ResponseEntity<Map<String,String>> completePayment(@RequestBody RazorpayResponseDTO dto, @PathVariable("appId") Long appId) throws Exception {
        return ResponseEntity.ok(paymentService.completePayment(dto,appId));
    }
}
