package com.example.CreditManagement.repositories;

import com.example.CreditManagement.models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ClientRepositoryTest {
    @Autowired
    private ClientRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindByEmail() {
        //given
        String fullName = "Some name";
        String email = "test@mail.com";
        int passportNumber = 1234;

        Client client = new Client();
        client.setFullName(fullName);
        client.setPassportNumber(passportNumber);
        client.setEmail(email);

        underTest.save(client);

        //when
        Optional<Client> foundedClient = underTest.findByEmail(email);
        //then
        assertThat(foundedClient.isPresent()).isTrue();
        assertEquals(fullName,foundedClient.get().getFullName());
        assertEquals(email,foundedClient.get().getEmail());
        assertEquals(passportNumber,foundedClient.get().getPassportNumber());
    }

    @Test
    void itShouldFindByPassportNumber() {
        //given
        String fullName = "Some name";
        String email = "test@mail.com";
        int passportNumber = 1234;

        Client client = new Client();
        client.setFullName(fullName);
        client.setPassportNumber(passportNumber);
        client.setEmail(email);

        underTest.save(client);

        //when
        Optional<Client> foundedClient = underTest.findByPassportNumber(passportNumber);
        //then
        assertThat(foundedClient.isPresent()).isTrue();
        assertEquals(fullName,foundedClient.get().getFullName());
        assertEquals(email,foundedClient.get().getEmail());
        assertEquals(passportNumber,foundedClient.get().getPassportNumber());
    }
}