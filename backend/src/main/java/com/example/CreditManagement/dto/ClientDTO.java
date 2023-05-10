package com.example.CreditManagement.dto;

import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.models.Credit;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private int id;
    private String fullName;
    private String email;
    private int passportNumber;
    private List<CreditDTO> credits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    public List<CreditDTO> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }

    public static Client toEntity(ClientDTO clientDTO){
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setFullName(clientDTO.getFullName());
        client.setEmail(clientDTO.getEmail());
        client.setPassportNumber(clientDTO.getPassportNumber());

        List<CreditDTO> creditDTOS = clientDTO.getCredits();

        if(creditDTOS != null) {

            List<Credit> credits = new ArrayList<>();

            for (CreditDTO creditDTO : creditDTOS) {
                credits.add(CreditDTO.toEntity(creditDTO));
            }

            client.setCredits(credits);
        }
        else
            client.setCredits(new ArrayList<>());
        return client;
    }
    public static ClientDTO fromEntity(Client client){
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId(client.getId());
        clientDTO.setFullName(client.getFullName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassportNumber(client.getPassportNumber());

        List<CreditDTO> creditDTOS = new ArrayList<>();
        List<Credit> credits = client.getCredits();

        for(Credit credit: credits){
            creditDTOS.add(CreditDTO.fromEntity(credit));
        }

        clientDTO.setCredits(creditDTOS);
        return clientDTO;
    }
}
