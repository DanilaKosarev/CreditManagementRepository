package com.example.CreditManagement.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Temporal(TemporalType.DATE)
    @Column(name="date_of_payment")
    private Date dateOfPayment;
    @Column(name="overall_amount", precision = 19, scale = 2)
    private BigDecimal overallAmount;
    @Column(name="credit_body_amount", precision = 19, scale = 2)
    private BigDecimal creditBodyAmount;
    @Column(name="percent_amount", precision = 19, scale = 2)
    private BigDecimal percentAmount;
    @ManyToOne
    @JoinColumn(name="credit_id", referencedColumnName = "id")
    private Credit credit;

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

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
