package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.CreditDTO;
import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.security.JWTFilter;
import com.example.CreditManagement.security.JWTUtil;
import com.example.CreditManagement.services.ClientService;
import com.example.CreditManagement.services.CreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.CoreMatchers;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = CreditsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CreditsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CreditService creditService;
    @MockBean
    private JWTFilter jwtFilter;
    @MockBean
    private JWTUtil jwtUtil;
    private final String controllerUrl = "/api/credits";

    @Test
    void shouldFindAllAndReturnEmptyList() throws Exception{
        given(creditService.findAll()).willReturn(Collections.emptyList());
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void shouldFindOneAndReturnFounded() throws Exception{
        //given
        int id = 1;
        BigDecimal amount = BigDecimal.valueOf(10000);
        int monthQuantity = 4;

        Credit credit = new Credit();
        credit.setId(id);
        credit.setAmount(amount);
        credit.setMonthQuantity(monthQuantity);
        credit.setClient(new Client());
        credit.setCreditOffer(new CreditOffer());
        credit.setPayments(Collections.emptyList());

        given(creditService.findOne(id)).willReturn(Optional.of(credit));
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl + "/"+id));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", CoreMatchers.is(amount.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.monthQuantity", CoreMatchers.is(monthQuantity)));
    }

    @Test
    void shouldCreateAndReturnCreated() throws Exception {
        //given
        int id = 1;
        BigDecimal amount = BigDecimal.valueOf(10000);
        int monthQuantity = 4;

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setId(id);
        creditDTO.setAmount(amount);
        creditDTO.setMonthQuantity(monthQuantity);
        creditDTO.setPayments(Collections.emptyList());

        given(creditService.create(anyInt(),anyInt(),any(),anyInt())).willReturn(CreditDTO.toEntity(creditDTO));
        //when
        ResultActions response = mockMvc.perform(post(controllerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditDTO)));
        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", CoreMatchers.is(amount.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.monthQuantity", CoreMatchers.is(monthQuantity)));
    }

    @Test
    void shouldValidateAndReturnFalse() throws Exception{
        //given
        String creditOfferId = "1";
        String amount = "10000";

        given(creditService.isExceed(anyInt(), any())).willReturn(false);
        //when
        ResultActions response = mockMvc.perform(get(controllerUrl+"/validate")
                .param("creditOfferId",creditOfferId)
                .param("amount", amount));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceed", CoreMatchers.is(false)));
    }

    @Test
    void shouldDeleteAndReturnOk() throws Exception{
        //given
        int id = 1;
        //when
        ResultActions response = mockMvc.perform(delete(controllerUrl+"/"+id));
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}