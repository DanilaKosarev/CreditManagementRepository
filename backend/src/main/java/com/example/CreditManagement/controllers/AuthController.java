package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.AuthenticationDTO;
import com.example.CreditManagement.dto.CurrentUserDTO;
import com.example.CreditManagement.models.CurrentUser;
import com.example.CreditManagement.security.JWTUtil;
import com.example.CreditManagement.services.CurrentUserService;
import com.example.CreditManagement.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;
    private final CurrentUserService currentUserService;
    @Autowired
    public AuthController(RegistrationService registrationService, JWTUtil jwtUtil, AuthenticationManager authenticationManager, CurrentUserService currentUserService) {
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.currentUserService = currentUserService;
    }
    @PostMapping("/registration")
    public boolean performRegistration(@RequestBody CurrentUserDTO currentUserDTO){
        CurrentUser currentUser = CurrentUserDTO.toEntity(currentUserDTO);

        boolean isValid = currentUserService.isValid(currentUser.getUsername());

        if(isValid)
            registrationService.register(currentUser);

        return isValid;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(),
                authenticationDTO.getPassword()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch(BadCredentialsException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return new ResponseEntity<>(Collections.singletonMap("jwt",token), HttpStatus.OK);
    }
    @PostMapping("/validate")
    public boolean performValidation(@RequestBody String username){
        return currentUserService.isValid(username);
    }
}
