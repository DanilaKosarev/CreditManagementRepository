package com.example.CreditManagement.repositories;

import com.example.CreditManagement.models.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Integer> {
}
