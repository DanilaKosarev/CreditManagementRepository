package com.example.CreditManagement.services;

import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.models.Payment;
import com.example.CreditManagement.repositories.ClientRepository;
import com.example.CreditManagement.repositories.CreditOfferRepository;
import com.example.CreditManagement.repositories.CreditRepository;
import com.example.CreditManagement.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CreditService {

    private final CreditRepository creditRepository;
    private final CreditOfferRepository creditOfferRepository;
    private final ClientRepository clientRepository;
    private final PaymentRepository paymentRepository;
    @Autowired
    public CreditService(CreditRepository creditRepository, CreditOfferRepository creditOfferRepository, ClientRepository clientRepository, PaymentRepository paymentRepository) {
        this.creditRepository = creditRepository;
        this.creditOfferRepository = creditOfferRepository;
        this.clientRepository = clientRepository;
        this.paymentRepository = paymentRepository;
    }
    public Optional<Credit> findOne(int id){
        return creditRepository.findById(id);
    }
    public List<Credit> findAll(){
        return creditRepository.findAll();
    }
    @Transactional
    public Credit create(int clientId, int creditOfferId, BigDecimal pureAmount, int monthQty){

        CreditOffer creditOffer = creditOfferRepository.findById(creditOfferId).get();
        Client client = clientRepository.findById(clientId).get();

        BigDecimal limit = creditOffer.getCreditLimit();
        BigDecimal percent = creditOffer.getPercent();

        BigDecimal creditAmount = pureAmount;
        BigDecimal percentAmount = creditAmount.multiply(percent.divide(BigDecimal.valueOf(100),2 , RoundingMode.CEILING));
        BigDecimal totalAmount = creditAmount.add(percentAmount);

        Credit credit = new Credit();
        credit.setCreditOffer(creditOffer);
        credit.setClient(client);
        credit.setAmount(totalAmount);
        credit.setMonthQuantity(monthQty);

        List<Payment> payments = new ArrayList<>();

        for(int i = 0; i < monthQty; i++){
            Payment payment = new Payment();
            payment.setCredit(credit);
            payment.setDateOfPayment(java.sql.Date.valueOf(LocalDate.now().plusMonths(i+1)));
            payment.setOverallAmount(totalAmount.divide(BigDecimal.valueOf(monthQty),2 ,RoundingMode.CEILING));

            payment.setPercentAmount(percentAmount.divide(BigDecimal.valueOf(monthQty), 2,RoundingMode.CEILING).multiply(BigDecimal.valueOf(monthQty-i)));
            payment.setCreditBodyAmount(creditAmount.divide(BigDecimal.valueOf(monthQty),2,RoundingMode.CEILING).multiply(BigDecimal.valueOf(monthQty-i)));

            paymentRepository.save(payment);
            payments.add(payment);
        }

        credit.setPayments(payments);

        return creditRepository.save(credit);
    }

    public boolean isExceed(int creditOfferId, BigDecimal pureAmount){
        CreditOffer creditOffer = creditOfferRepository.findById(creditOfferId).get();

        BigDecimal limit = creditOffer.getCreditLimit();
        BigDecimal percent = creditOffer.getPercent();

        BigDecimal creditAmount = pureAmount;
        BigDecimal percentAmount = creditAmount.multiply(percent.divide(BigDecimal.valueOf(100),2 , RoundingMode.CEILING));
        BigDecimal totalAmount = creditAmount.add(percentAmount);

        return limit.compareTo(totalAmount) < 0;
    }
    @Transactional
    public Credit update(Credit credit){
        return creditRepository.save(credit);
    }
    @Transactional
    public void delete(int id){
        creditRepository.deleteById(id);
    }
}
