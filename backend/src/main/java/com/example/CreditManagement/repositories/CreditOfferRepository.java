package com.example.CreditManagement.repositories;

import com.example.CreditManagement.models.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfferRepository extends JpaRepository<CreditOffer, Integer> {
}
