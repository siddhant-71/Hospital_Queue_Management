package com.hospital.queue.DTO;

public class RazorpayResponseDTO {
    private String razorpayOrderId;
    private String razorpayPaymentID;
    private String razorpaySignature;

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentID() {
        return razorpayPaymentID;
    }

    public void setRazorpayPaymentID(String razorpayPaymentID) {
        this.razorpayPaymentID = razorpayPaymentID;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }
}
