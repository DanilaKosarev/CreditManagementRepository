package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CurrentUser;
import com.example.CreditManagement.repositories.CurrentUsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrentUserServiceTest {
    @Mock
    private CurrentUsersRepository currentUsersRepository;
    private CurrentUserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CurrentUserService(currentUsersRepository);
    }

    @Test
    void shouldValidate() {
        //given
        String username = "username";
        //when
        underTest.isValid(username);
        //then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(currentUsersRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertThat(capturedUsername).isEqualTo(username);

    }
}