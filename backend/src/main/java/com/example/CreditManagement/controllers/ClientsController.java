package com.example.CreditManagement.controllers;

import com.example.CreditManagement.dto.ClientDTO;
import com.example.CreditManagement.dto.ClientValidationDTO;
import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientsController {
    private final ClientService clientService;

    @Autowired
    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping
    public List<ClientDTO> findAll(){
        List<Client> clients = clientService.findAll();
        List<ClientDTO> clientDTOS = new ArrayList<>();

        for(Client client: clients){
            clientDTOS.add(ClientDTO.fromEntity(client));
        }
        return clientDTOS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findOne(@PathVariable(name="id") int id){
        Optional<Client> client = clientService.findOne(id);
        return client.map(c -> new ResponseEntity<>(ClientDTO.fromEntity(c), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    @GetMapping("/validate")
    public ResponseEntity<ClientValidationDTO> validate(@RequestParam(name="email")String email,
                                                        @RequestParam(name="passportNumber")int passportNumber,
                                                        @RequestParam(name="id")int id){
        ClientValidationDTO clientValidationDTO = new ClientValidationDTO();

        if(id == 0){
            clientValidationDTO.setEmailOccupied(clientService.findByEmail(email).isPresent());
            clientValidationDTO.setPassportNumberOccupied(clientService.findByPassportNumber(passportNumber).isPresent());
        }
        else{
            clientValidationDTO.setEmailOccupied(clientService.findByEmail(email).isPresent() && clientService.findByEmail(email).get().getId() != id);

            clientValidationDTO.setPassportNumberOccupied(clientService.findByPassportNumber(passportNumber).isPresent() && clientService.findByPassportNumber(passportNumber).get().getId() != id);
        }
        return new ResponseEntity<>(clientValidationDTO,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO){
        return new ResponseEntity<>(ClientDTO.fromEntity(clientService.create(ClientDTO.toEntity(clientDTO))), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO){
        return new ResponseEntity<>(ClientDTO.fromEntity(clientService.update(ClientDTO.toEntity(clientDTO))), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name="id")int id){
        clientService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
