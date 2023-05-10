package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.repositories.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CreditOfferService {
    private final CreditOfferRepository creditOfferRepository;

    @Autowired
    public CreditOfferService(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }
    public Optional<CreditOffer> findOne(int id){
        return creditOfferRepository.findById(id);
    }
    public List<CreditOffer> findAll(){
        return creditOfferRepository.findAll();
    }
    @Transactional
    public CreditOffer create(CreditOffer creditOffer){
        return creditOfferRepository.save(creditOffer);
    }
    @Transactional
    public CreditOffer update(CreditOffer creditOffer){
        return creditOfferRepository.save(creditOffer);
    }
    @Transactional
    public void delete(int id){
        creditOfferRepository.deleteById(id);
    }
}
