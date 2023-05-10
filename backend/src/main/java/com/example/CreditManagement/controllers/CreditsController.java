package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.CreateCreditDTO;
import com.example.CreditManagement.dto.CreditDTO;
import com.example.CreditManagement.models.Credit;
import com.example.CreditManagement.services.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/credits")
public class CreditsController {
    private final CreditService creditService;
    @Autowired
    public CreditsController(CreditService creditService) {
        this.creditService = creditService;
    }
    @GetMapping
    public List<CreditDTO> findAll(){
        List<Credit> credits = creditService.findAll();
        List<CreditDTO> creditDTOS = new ArrayList<>();

        for(Credit credit: credits){
            creditDTOS.add(CreditDTO.fromEntity(credit));
        }
        return creditDTOS;
    }
    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> findOne(@PathVariable(name="id") int id){
        Optional<Credit> credit = creditService.findOne(id);
        return credit.map(c -> new ResponseEntity<>(CreditDTO.fromEntity(c), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CreditDTO> create(@RequestBody CreateCreditDTO createCreditDTO){
        return new ResponseEntity<>(CreditDTO.fromEntity(creditService.create(createCreditDTO.getClientId(),
                                    createCreditDTO.getCreditOfferId(),
                                    createCreditDTO.getPureAmount(),
                                    createCreditDTO.getMonthQuantity())),HttpStatus.CREATED);
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validate(@RequestParam(name="creditOfferId") int creditOfferId,
                                        @RequestParam(name="amount") BigDecimal amount){
        return new ResponseEntity<>(Collections.singletonMap("exceed",creditService.isExceed(creditOfferId, amount)),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name="id")int id){
        creditService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
