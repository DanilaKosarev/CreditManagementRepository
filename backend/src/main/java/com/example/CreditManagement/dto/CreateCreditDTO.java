package com.example.CreditManagement.dto;

import java.math.BigDecimal;

public class CreateCreditDTO {
    private int clientId;
    private int creditOfferId;
    private BigDecimal pureAmount;
    private int monthQuantity;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCreditOfferId() {
        return creditOfferId;
    }

    public void setCreditOfferId(int creditOfferId) {
        this.creditOfferId = creditOfferId;
    }

    public BigDecimal getPureAmount() {
        return pureAmount;
    }

    public void setPureAmount(BigDecimal pureAmount) {
        this.pureAmount = pureAmount;
    }

    public int getMonthQuantity() {
        return monthQuantity;
    }

    public void setMonthQuantity(int monthQuantity) {
        this.monthQuantity = monthQuantity;
    }
}
