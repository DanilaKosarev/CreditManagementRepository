package com.example.CreditManagement.dto;

import com.example.CreditManagement.models.Payment;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDTO {
    private int id;
    private Date dateOfPayment;
    private BigDecimal overallAmount;
    private BigDecimal creditBodyAmount;
    private BigDecimal percentAmount;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDateOfPayment() {
        return dateOfPayment;
    }
    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
    public BigDecimal getOverallAmount() {
        return overallAmount;
    }
    public void setOverallAmount(BigDecimal overallAmount) {
        this.overallAmount = overallAmount;
    }
    public BigDecimal getCreditBodyAmount() {
        return creditBodyAmount;
    }
    public void setCreditBodyAmount(BigDecimal creditBodyAmount) {
        this.creditBodyAmount = creditBodyAmount;
    }
    public BigDecimal getPercentAmount() {
        return percentAmount;
    }
    public void setPercentAmount(BigDecimal percentAmount) {
        this.percentAmount = percentAmount;
    }
    public static PaymentDTO fromEntity(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(payment.getId());
        paymentDTO.setDateOfPayment(payment.getDateOfPayment());
        paymentDTO.setOverallAmount(payment.getOverallAmount());
        paymentDTO.setCreditBodyAmount(payment.getCreditBodyAmount());
        paymentDTO.setPercentAmount(payment.getPercentAmount());

        return paymentDTO;
    }

    public static Payment toEntity(PaymentDTO paymentDTO){
        Payment payment = new Payment();

        payment.setId(paymentDTO.getId());
        payment.setDateOfPayment(paymentDTO.getDateOfPayment());
        payment.setOverallAmount(paymentDTO.getOverallAmount());
        payment.setCreditBodyAmount(paymentDTO.getCreditBodyAmount());
        payment.setPercentAmount(paymentDTO.getPercentAmount());

        return payment;
    }
}
