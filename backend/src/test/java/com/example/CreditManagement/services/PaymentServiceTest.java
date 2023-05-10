package com.example.CreditManagement.services;

import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.models.Payment;
import com.example.CreditManagement.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    private PaymentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PaymentService(paymentRepository);
    }

    @Test
    void shouldCreate() {
        //given
        Payment payment = new Payment();
        payment.setCredit(new Credit());
        payment.setOverallAmount(BigDecimal.valueOf(200));
        payment.setCreditBodyAmount(BigDecimal.valueOf(100));
        payment.setPercentAmount(BigDecimal.valueOf(10));
        payment.setDateOfPayment(new Date());
        //when
        underTest.create(payment);
        //then
        ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentArgumentCaptor.capture());
        Payment capturedPayment = paymentArgumentCaptor.getValue();
        assertThat(capturedPayment).isEqualTo(payment);
    }

    @Test
    void shouldDelete() {
        //given
        int id = 1;
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentRepository).deleteById(idArgumentCaptor.capture());
        int capturedId = idArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }
}