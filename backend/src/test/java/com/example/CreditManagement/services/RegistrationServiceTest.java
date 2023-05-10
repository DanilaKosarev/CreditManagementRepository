package com.example.CreditManagement.services;

import com.example.CreditManagement.models.CurrentUser;
import com.example.CreditManagement.repositories.CurrentUsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    @Mock
    private CurrentUsersRepository currentUsersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private RegistrationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RegistrationService(currentUsersRepository, passwordEncoder);
    }

    @Test
    void shouldRegister() {
        //given
        CurrentUser currentUser = new CurrentUser();
        currentUser.setUsername("user");
        currentUser.setPassword("user");
        //when
        underTest.register(currentUser);
        //then
        ArgumentCaptor<CurrentUser> currentUserArgumentCaptor = ArgumentCaptor.forClass(CurrentUser.class);
        verify(currentUsersRepository).save(currentUserArgumentCaptor.capture());
        CurrentUser capturedCurrentUser = currentUserArgumentCaptor.getValue();
        assertThat(capturedCurrentUser).isEqualTo(currentUser);
    }
}