package com.example.CreditManagement.dto;

import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.models.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreditDTO {
    private int id;
    private BigDecimal amount;
    private int monthQuantity;
    private List<PaymentDTO> payments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getMonthQuantity() {
        return monthQuantity;
    }

    public void setMonthQuantity(int monthQuantity) {
        this.monthQuantity = monthQuantity;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }

    public static Credit toEntity(CreditDTO creditDTO){
        Credit credit = new Credit();

        credit.setId(creditDTO.getId());
        credit.setAmount(creditDTO.getAmount());
        credit.setMonthQuantity(creditDTO.getMonthQuantity());

        List<PaymentDTO> paymentDTOS = creditDTO.getPayments();
        List<Payment> payments = new ArrayList<>();

        for(PaymentDTO paymentDTO: paymentDTOS){
            payments.add(PaymentDTO.toEntity(paymentDTO));
        }

        credit.setPayments(payments);

        return credit;
    }

    public static CreditDTO fromEntity(Credit credit){
        CreditDTO creditDTO = new CreditDTO();

        creditDTO.setId(credit.getId());
        creditDTO.setAmount(credit.getAmount());
        creditDTO.setMonthQuantity(credit.getMonthQuantity());

        List<Payment> payments = credit.getPayments();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();

        for(Payment payment:payments){
            paymentDTOS.add(PaymentDTO.fromEntity(payment));
        }

        creditDTO.setPayments(paymentDTOS);

        return creditDTO;
    }
}
