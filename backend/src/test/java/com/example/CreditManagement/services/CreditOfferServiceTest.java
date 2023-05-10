package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.repositories.CreditOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class CreditOfferServiceTest {
    @Mock
    private CreditOfferRepository creditOfferRepository;
    private CreditOfferService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CreditOfferService(creditOfferRepository);
    }

    @Test
    void shouldFindOne() {
        //given
        int id = 1;
        //when
        underTest.findOne(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(creditOfferRepository).findById(idArgumentCaptor.capture());
        int capturedId = idArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void shouldFindAll() {
        //when
        underTest.findAll();
        //then
        verify(creditOfferRepository).findAll();
    }

    @Test
    void shouldCreate() {
        //given
        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setPercent(BigDecimal.valueOf(10));
        creditOffer.setCreditLimit(BigDecimal.valueOf(10000));
        //when
        underTest.create(creditOffer);
        //then
        ArgumentCaptor<CreditOffer> creditOfferArgumentCaptor = ArgumentCaptor.forClass(CreditOffer.class);
        verify(creditOfferRepository).save(creditOfferArgumentCaptor.capture());
        CreditOffer capturedCreditOffer = creditOfferArgumentCaptor.getValue();
        assertThat(capturedCreditOffer).isEqualTo(creditOffer);
    }

    @Test
    void shouldUpdate() {
        //given
        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setPercent(BigDecimal.valueOf(10));
        creditOffer.setCreditLimit(BigDecimal.valueOf(10000));
        //when
        underTest.update(creditOffer);
        //then
        ArgumentCaptor<CreditOffer> creditOfferArgumentCaptor = ArgumentCaptor.forClass(CreditOffer.class);
        verify(creditOfferRepository).save(creditOfferArgumentCaptor.capture());
        CreditOffer capturedCreditOffer = creditOfferArgumentCaptor.getValue();
        assertThat(capturedCreditOffer).isEqualTo(creditOffer);
    }

    @Test
    void shouldDelete() {
        //given
        int id = 1;
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(creditOfferRepository).deleteById(idArgumentCaptor.capture());
        int capturedId = idArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }
}