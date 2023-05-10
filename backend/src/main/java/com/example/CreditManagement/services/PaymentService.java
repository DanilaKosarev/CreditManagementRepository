package com.example.CreditManagement.services;

import com.example.CreditManagement.models.Payment;
import com.example.CreditManagement.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> findOne(int id){
        return paymentRepository.findById(id);
    }
    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }
    @Transactional
    public Payment create(Payment payment){
        return paymentRepository.save(payment);
    }
    @Transactional
    public Payment update(Payment payment){
        return paymentRepository.save(payment);
    }
    @Transactional
    public void delete(int id){
        paymentRepository.deleteById(id);
    }
}
