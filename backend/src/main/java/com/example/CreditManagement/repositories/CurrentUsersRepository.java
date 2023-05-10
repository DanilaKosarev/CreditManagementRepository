package com.example.CreditManagement.repositories;

import com.example.CreditManagement.models.CurrentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrentUsersRepository extends JpaRepository<CurrentUser, Integer> {
    Optional<CurrentUser> findByUsername(String username);
}
