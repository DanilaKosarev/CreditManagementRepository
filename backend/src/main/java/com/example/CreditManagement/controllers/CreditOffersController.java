package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.CreditOfferDTO;
import com.example.CreditManagement.models.CreditOffer;
import com.example.CreditManagement.services.CreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-offers")
public class CreditOffersController {
    private final CreditOfferService creditOfferService;
    @Autowired
    public CreditOffersController(CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
    }
    @GetMapping
    public List<CreditOfferDTO> findAll(){
        List<CreditOffer> creditOffers = creditOfferService.findAll();
        List<CreditOfferDTO> creditOfferDTOS = new ArrayList<>();

        for(CreditOffer creditOffer: creditOffers){
            creditOfferDTOS.add(CreditOfferDTO.fromEntity(creditOffer));
        }
        return creditOfferDTOS;
    }
    @GetMapping("/{id}")
    public ResponseEntity<CreditOfferDTO> findOne(@PathVariable(name="id") int id){
        Optional<CreditOffer> creditOffer = creditOfferService.findOne(id);
        return creditOffer.map(c -> new ResponseEntity<>(CreditOfferDTO.fromEntity(c), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<CreditOfferDTO> create(@RequestBody CreditOfferDTO creditOfferDTO){
        return new ResponseEntity<>(CreditOfferDTO.fromEntity(creditOfferService.create(CreditOfferDTO.toEntity(creditOfferDTO))), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<CreditOfferDTO> update(@RequestBody CreditOfferDTO creditOfferDTO){
        return new ResponseEntity<>(CreditOfferDTO.fromEntity(creditOfferService.update(CreditOfferDTO.toEntity(creditOfferDTO))), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name="id")int id){
        creditOfferService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
