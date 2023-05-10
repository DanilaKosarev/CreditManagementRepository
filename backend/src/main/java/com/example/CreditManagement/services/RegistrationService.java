package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CurrentUser;
import com.example.CreditManagement.repositories.CurrentUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final CurrentUsersRepository currentUsersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(CurrentUsersRepository currentUsersRepository, PasswordEncoder passwordEncoder) {
        this.currentUsersRepository = currentUsersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void register(CurrentUser currentUser){
        currentUser.setPassword(passwordEncoder.encode(currentUser.getPassword()));
        currentUsersRepository.save(currentUser);
    }
}
