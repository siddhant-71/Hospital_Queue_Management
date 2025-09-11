package com.hospital.queue.DTO;

public class RazorpayResponseDTO {
    private String RazorpayOrderId;
    private String RazorpayPaymentID;
    private String RazorpaySignature;

    public String getRazorpayOrderId() {
        return RazorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        RazorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentID() {
        return RazorpayPaymentID;
    }

    public void setRazorpayPaymentID(String razorpayPaymentID) {
        RazorpayPaymentID = razorpayPaymentID;
    }

    public String getRazorpaySignature() {
        return RazorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        RazorpaySignature = razorpaySignature;
    }
}
