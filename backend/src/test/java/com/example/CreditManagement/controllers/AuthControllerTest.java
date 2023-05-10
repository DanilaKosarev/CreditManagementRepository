package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.AuthenticationDTO;
import com.example.CreditManagement.dto.CurrentUserDTO;
import com.example.CreditManagement.security.JWTFilter;
import com.example.CreditManagement.security.JWTUtil;
import com.example.CreditManagement.services.CurrentUserService;
import com.example.CreditManagement.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private CurrentUserService currentUserService;
    @MockBean
    private JWTFilter jwtFilter;
    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private AuthenticationManager authenticationManager;

    private final String controllerUrl = "/api/auth";

    @Test
    void shouldPerformRegistrationAndReturnTrue() throws Exception{
        //given
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        currentUserDTO.setUsername("user");
        currentUserDTO.setPassword("user");

        given(currentUserService.isValid(any())).willReturn(true);
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl+"/registration")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentUserDTO))
        );
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void shouldPerformSuccessLoginAndReturnToken() throws Exception{
        //given
        String username = "user";
        String tokenPayload = "token_payload";

        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername(username);
        authenticationDTO.setPassword("user");

        given(authenticationManager.authenticate(any())).willReturn(any());
        given(jwtUtil.generateToken(username)).willReturn(tokenPayload);
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl+"/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwt", CoreMatchers.is(tokenPayload)));
    }
    @Test
    void shouldNotPerformLoginAndReturnBadRequest() throws Exception {
        //given
        String username = "user";

        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setUsername(username);
        authenticationDTO.setPassword("user");

        given(authenticationManager.authenticate(any())).willThrow(BadCredentialsException.class);
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl+"/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldPerformPositiveValidationAndReturnTrue() throws Exception{
        //given
        String username = "username";
        given(currentUserService.isValid(username)).willReturn(true);
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl+"/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(username));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void shouldPerformNegativeValidationAndReturnFalse() throws Exception{
        //given
        String username = "username";
        given(currentUserService.isValid(username)).willReturn(false);
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl+"/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(username));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }
}