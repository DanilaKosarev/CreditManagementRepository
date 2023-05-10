package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CurrentUser;
import com.example.CreditManagement.repositories.CurrentUsersRepository;
import com.example.CreditManagement.security.CurrentUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private final CurrentUsersRepository currentUsersRepository;
    @Autowired
    public CurrentUserDetailsService(CurrentUsersRepository currentUsersRepository) {
        this.currentUsersRepository = currentUsersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CurrentUser> currentUser = currentUsersRepository.findByUsername(username);

        if(currentUser.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new CurrentUserDetails(currentUser.get());
    }
}
