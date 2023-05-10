package com.example.CreditManagement.services;

import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.repositories.ClientRepository;
import com.example.CreditManagement.repositories.CreditOfferRepository;
import com.example.CreditManagement.repositories.CreditRepository;
import com.example.CreditManagement.repositories.PaymentRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private CreditOfferRepository creditOfferRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private PaymentRepository paymentRepository;

    private CreditService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CreditService(creditRepository,creditOfferRepository,clientRepository,paymentRepository);
    }

    @Test
    void shouldFindOne() {
        //given
        int id = 1;
        //when
        underTest.findOne(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(creditRepository).findById(idArgumentCaptor.capture());
        int capturedId = idArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void shouldFindAll() {
        //when
        underTest.findAll();
        //then
        verify(creditRepository).findAll();
    }

    @Test
    void shouldCreate() {
        //given
        int clientId = 1;
        int creditOfferId = 1;
        BigDecimal pureAmount = BigDecimal.valueOf(1000);
        int monthQty = 4;

        Client client = new Client();
        client.setFullName("Some name");
        client.setEmail("some@mail.com");
        client.setPassportNumber(1234);
        client.setId(clientId);

        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setCreditLimit(BigDecimal.valueOf(20000));
        creditOffer.setPercent(BigDecimal.valueOf(10));
        creditOffer.setId(creditOfferId);

        given(creditOfferRepository.findById(creditOfferId)).willReturn(Optional.of(creditOffer));
        given(clientRepository.findById(clientId)).willReturn(Optional.of(client));

        //when
        underTest.create(clientId,creditOfferId,pureAmount,monthQty);
        //then
        ArgumentCaptor<Integer> creditOfferIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(creditOfferRepository).findById(creditOfferIdArgumentCaptor.capture());
        int capturedCreditOfferId = creditOfferIdArgumentCaptor.getValue();
        assertThat(capturedCreditOfferId).isEqualTo(creditOfferId);

        ArgumentCaptor<Integer> clientIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(clientRepository).findById(clientIdArgumentCaptor.capture());
        int capturedClientId = clientIdArgumentCaptor.getValue();
        assertThat(capturedClientId).isEqualTo(clientId);

        verify(paymentRepository,times(monthQty)).save(any());

        ArgumentCaptor<Credit> creditArgumentCaptor = ArgumentCaptor.forClass(Credit.class);
        verify(creditRepository).save(creditArgumentCaptor.capture());
        Credit capturedCredit = creditArgumentCaptor.getValue();
        assertEquals(monthQty, capturedCredit.getPayments().size());

    }

    @Test
    void shouldReturnFalse() {
        //given
        int creditOfferId = 1;
        BigDecimal pureAmount = BigDecimal.valueOf(1000);

        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setCreditLimit(BigDecimal.valueOf(20000));
        creditOffer.setPercent(BigDecimal.valueOf(10));
        creditOffer.setId(creditOfferId);

        given(creditOfferRepository.findById(creditOfferId)).willReturn(Optional.of(creditOffer));
        //when
        underTest.isExceed(creditOfferId,pureAmount);
        //then
        ArgumentCaptor<Integer> creditOfferIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(creditOfferRepository).findById(creditOfferIdArgumentCaptor.capture());
        int capturedCreditOfferId = creditOfferIdArgumentCaptor.getValue();
        assertThat(capturedCreditOfferId).isEqualTo(creditOfferId);

        assertFalse(underTest.isExceed(creditOfferId,pureAmount));
    }

    @Test
    void shouldDelete() {
        //given
        int id = 1;
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(creditRepository).deleteById(idArgumentCaptor.capture());
        int capturedId = idArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }
}