package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.CreditOfferDTO;
import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.security.JWTFilter;
import com.example.CreditManagement.security.JWTUtil;
import com.example.CreditManagement.services.ClientService;
import com.example.CreditManagement.services.CreditOfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.math.BigDecimal;
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
@WebMvcTest(controllers = CreditOffersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CreditOffersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CreditOfferService creditOfferService;
    @MockBean
    private JWTFilter jwtFilter;
    @MockBean
    private JWTUtil jwtUtil;
    private final String controllerUrl = "/api/credit-offers";

    @Test
    void shouldFindAllAndReturnEmptyList() throws Exception{
        given(creditOfferService.findAll()).willReturn(Collections.emptyList());
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
        BigDecimal creditLimit = BigDecimal.valueOf(1000);
        BigDecimal percent = BigDecimal.valueOf(10);

        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setId(id);
        creditOffer.setCreditLimit(creditLimit);
        creditOffer.setPercent(percent);
        creditOffer.setCredits(Collections.emptyList());

        given(creditOfferService.findOne(id)).willReturn(Optional.of(creditOffer));
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl + "/"+id));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditLimit", CoreMatchers.is(creditLimit.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.percent", CoreMatchers.is(percent.intValue())));
    }

    @Test
    void shouldCreateAndReturnCreated() throws Exception{
        //given
        int id = 1;
        BigDecimal creditLimit = BigDecimal.valueOf(1000);
        BigDecimal percent = BigDecimal.valueOf(10);

        CreditOfferDTO creditOfferDTO = new CreditOfferDTO();
        creditOfferDTO.setId(id);
        creditOfferDTO.setCreditLimit(creditLimit);
        creditOfferDTO.setPercent(percent);
        creditOfferDTO.setCredits(new ArrayList<>());

        given(creditOfferService.create(any())).willReturn(CreditOfferDTO.toEntity(creditOfferDTO));
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditOfferDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditLimit", CoreMatchers.is(creditLimit.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.percent", CoreMatchers.is(percent.intValue())));
    }

    @Test
    void shouldUpdateAndReturnUpdated() throws Exception {
        //given
        int id = 1;
        BigDecimal creditLimit = BigDecimal.valueOf(1000);
        BigDecimal percent = BigDecimal.valueOf(10);

        CreditOfferDTO creditOfferDTO = new CreditOfferDTO();
        creditOfferDTO.setId(id);
        creditOfferDTO.setCreditLimit(creditLimit);
        creditOfferDTO.setPercent(percent);
        creditOfferDTO.setCredits(new ArrayList<>());

        given(creditOfferService.update(any())).willReturn(CreditOfferDTO.toEntity(creditOfferDTO));
        //when
        ResultActions response = mockMvc.perform(put(controllerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditOfferDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditLimit", CoreMatchers.is(creditLimit.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.percent", CoreMatchers.is(percent.intValue())));
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