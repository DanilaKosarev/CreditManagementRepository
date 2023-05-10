package com.example.CreditManagement.services;

import com.example.CreditManagement.models.Client;
import com.example.CreditManagement.repositories.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    private ClientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClientService(clientRepository);
    }

    @Test
    void shouldFindOne() {
        //given
        int id = 1;
        //when
        underTest.findOne(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(clientRepository).findById(idArgumentCaptor.capture());

        int capturedId = idArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void shouldFindAll() {
        //when
        underTest.findAll();
        //then
        verify(clientRepository).findAll();
    }

    @Test
    void shouldFindByEmail() {
        //given
        String email = "mail@mail.com";
        //when
        underTest.findByEmail(email);
        //then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(clientRepository).findByEmail(emailArgumentCaptor.capture());
        String capturedEmail = emailArgumentCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(email);
    }

    @Test
    void shouldFindByPassportNumber() {
        //given
        int passportNumber = 1234;
        //when
        underTest.findByPassportNumber(1234);
        //then
        ArgumentCaptor<Integer> passportNumberArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(clientRepository).findByPassportNumber(passportNumberArgumentCaptor.capture());
        int capturedPassportNumber = passportNumberArgumentCaptor.getValue();
        assertThat(capturedPassportNumber).isEqualTo(passportNumber);
    }

    @Test
    void shouldCreate() {
        //given
        Client client = new Client();
        client.setFullName("Some name");
        client.setEmail("mail@mail.com");
        client.setPassportNumber(1234);
        //when
        underTest.create(client);
        //then
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(client);
    }

    @Test
    void shouldUpdate() {
        //given
        Client client = new Client();
        client.setFullName("Some name");
        client.setEmail("mail@mail.com");
        client.setPassportNumber(1234);
        //when
        underTest.update(client);
        //then
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(client);
    }

    @Test
    void shouldDelete() {
        //given
        int id = 1;
        //when
        underTest.delete(id);
        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(clientRepository).deleteById(idArgumentCaptor.capture());
        int capturedId = idArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }
}