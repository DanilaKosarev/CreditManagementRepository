package com.example.CreditManagement.models;

import com.example.CreditManagement.dto.CreditOfferDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name="credit_offer")
public class CreditOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="credit_limit", precision = 19, scale = 2)
    private BigDecimal creditLimit;
    @Column(name="percent", precision = 19, scale = 2)
    private BigDecimal percent;
    @OneToMany(mappedBy = "creditOffer")
    private List<Credit> credits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }
}
