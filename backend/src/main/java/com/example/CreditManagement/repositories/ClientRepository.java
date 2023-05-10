package com.example.CreditManagement.repositories;

import com.example.CreditManagement.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByPassportNumber(int passportNumber);
}
