package com.hospital.queue.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appointmentId;

    private String RazorpayPaymentID;

    private String RazorpayOrderID;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentTime;

    @PrePersist
    protected void onCreate(){
        this.paymentTime=LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getAppointment() {
        return appointmentId;
    }

    public void setAppointment(Long appointment) {
        this.appointmentId = appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getRazorpayOrderID() {
        return RazorpayOrderID;
    }

    public void setRazorpayOrderID(String razorpayOrderID) {
        RazorpayOrderID = razorpayOrderID;
    }

    public String getRazorpayPaymentID() {
        return RazorpayPaymentID;
    }

    public void setRazorpayPaymentID(String razorpayPaymentID) {
        RazorpayPaymentID = razorpayPaymentID;
    }
}
