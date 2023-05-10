package com.example.CreditManagement.dto;

import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.models.CreditOffer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreditOfferDTO {
    private int id;
    private BigDecimal creditLimit;
    private BigDecimal percent;
    private List<CreditDTO> credits;

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

    public List<CreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }

    public static CreditOffer toEntity(CreditOfferDTO creditOfferDTO){
        CreditOffer creditOffer = new CreditOffer();

        creditOffer.setId(creditOfferDTO.getId());
        creditOffer.setCreditLimit(creditOfferDTO.getCreditLimit());
        creditOffer.setPercent(creditOfferDTO.getPercent());

        List<CreditDTO> creditDTOS = creditOfferDTO.getCredits();

        if(creditDTOS != null) {
            List<Credit> credits = new ArrayList<>();

            for (CreditDTO creditDTO : creditDTOS) {
                credits.add(CreditDTO.toEntity(creditDTO));
            }

            creditOffer.setCredits(credits);
        }
        else
            creditOffer.setCredits(new ArrayList<>());
        return creditOffer;
    }

    public static CreditOfferDTO fromEntity(CreditOffer creditOffer){
        CreditOfferDTO creditOfferDTO = new CreditOfferDTO();

        creditOfferDTO.setId(creditOffer.getId());
        creditOfferDTO.setCreditLimit(creditOffer.getCreditLimit());
        creditOfferDTO.setPercent(creditOffer.getPercent());

        List<CreditDTO> creditDTOS = new ArrayList<>();
        List<Credit> credits = creditOffer.getCredits();

        for(Credit credit: credits){
            creditDTOS.add(CreditDTO.fromEntity(credit));
        }

        creditOfferDTO.setCredits(creditDTOS);

        return creditOfferDTO;
    }
}
