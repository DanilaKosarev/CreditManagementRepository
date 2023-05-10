package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.ClientDTO;
import com.example.CreditManagement.dto.CurrentUserDTO;
import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.security.JWTFilter;
import com.example.CreditManagement.security.JWTUtil;
import com.example.CreditManagement.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = ClientsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ClientsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientService clientService;
    @MockBean
    private JWTFilter jwtFilter;
    @MockBean
    private JWTUtil jwtUtil;
    private final String controllerUrl = "/api/clients";

    @Test
    void shouldFindAllAndReturnEmptyList() throws Exception{
        given(clientService.findAll()).willReturn(Collections.emptyList());
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void shouldFindAndReturnOne() throws Exception{
        //given
        int id = 1;
        String fullName = "Some name";
        String email = "some@mail.com";
        int passportNumber = 1234;

        Client client = new Client();
        client.setId(id);
        client.setFullName(fullName);
        client.setEmail(email);
        client.setPassportNumber(passportNumber);
        client.setCredits(new ArrayList<>());

        given(clientService.findOne(id)).willReturn(Optional.of(client));
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl + "/"+id));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(fullName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", CoreMatchers.is(passportNumber)));
    }

    @Test
    void shouldValidateAndReturnTrue() throws Exception{
        //given
        String email = "some@mail.com";
        String passportNumber = "1234";
        String id = "0";
        given(clientService.findByEmail(email)).willReturn(Optional.empty());
        given(clientService.findByPassportNumber(Integer.parseInt(passportNumber))).willReturn(Optional.empty());
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl + "/validate")
                .param("email",email)
                .param("passportNumber", passportNumber)
                .param("id", id));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailOccupied", CoreMatchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumberOccupied", CoreMatchers.is(false)));
    }

    @Test
    void shouldCreateAndReturnCreated() throws Exception{
        //given
        int id = 1;
        String fullName = "Some name";
        String email = "some@mail.com";
        int passportNumber = 1234;

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(id);
        clientDTO.setFullName(fullName);
        clientDTO.setEmail(email);
        clientDTO.setPassportNumber(passportNumber);
        clientDTO.setCredits(new ArrayList<>());

        given(clientService.create(any())).willReturn(ClientDTO.toEntity(clientDTO));
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(fullName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", CoreMatchers.is(passportNumber)));
    }

    @Test
    void shouldUpdateAndReturnUpdated() throws Exception {
        //given
        int id = 1;
        String fullName = "Some name";
        String email = "some@mail.com";
        int passportNumber = 1234;

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(id);
        clientDTO.setFullName(fullName);
        clientDTO.setEmail(email);
        clientDTO.setPassportNumber(passportNumber);
        clientDTO.setCredits(new ArrayList<>());

        given(clientService.update(any())).willReturn(ClientDTO.toEntity(clientDTO));
        //when
        ResultActions response = mockMvc.perform(put(controllerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", CoreMatchers.is(fullName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(email)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber", CoreMatchers.is(passportNumber)));
    }

    @Test
    void shouldDeleteAndReturnOk() throws Exception{
        //given
        int id = 1;

        //when
        ResultActions response = mockMvc.perform(delete(controllerUrl + "/"+id));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}