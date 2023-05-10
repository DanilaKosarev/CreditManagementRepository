package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CurrentUser;
import com.example.CreditManagement.repositories.CurrentUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurrentUserService {
    private final CurrentUsersRepository currentUsersRepository;
    @Autowired
    public CurrentUserService(CurrentUsersRepository currentUsersRepository) {
        this.currentUsersRepository = currentUsersRepository;
    }

    public boolean isValid(String username){
        return currentUsersRepository.findByUsername(username).isEmpty();
    }

}
