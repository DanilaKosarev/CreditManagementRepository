package com.example.CreditManagement.services;

import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public Optional<Client> findOne(int id){
        return clientRepository.findById(id);
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Optional<Client> findByEmail(String email){
        return clientRepository.findByEmail(email);
    }
    public Optional<Client> findByPassportNumber(int passportNumber){
        return clientRepository.findByPassportNumber(passportNumber);
    }
    @Transactional
    public Client create(Client client){
        return clientRepository.save(client);
    }
    @Transactional
    public Client update(Client client){
        return clientRepository.save(client);
    }
    @Transactional
    public void delete(int id){
        clientRepository.deleteById(id);
    }
}
