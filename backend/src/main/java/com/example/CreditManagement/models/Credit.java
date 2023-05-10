package com.example.CreditManagement.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="amount", precision = 19, scale = 2)
    private BigDecimal amount;
    @Column(name="month_quantity")
    private int monthQuantity;
    @ManyToOne
    @JoinColumn(name="client_id", referencedColumnName = "id")
    private Client client;
    @ManyToOne
    @JoinColumn(name="credit_offer_id", referencedColumnName = "id")
    private CreditOffer creditOffer;
    @OneToMany(mappedBy = "credit")
    private List<Payment> payments;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
