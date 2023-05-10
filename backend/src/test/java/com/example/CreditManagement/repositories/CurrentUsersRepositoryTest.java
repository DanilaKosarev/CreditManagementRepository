package com.example.CreditManagement.repositories;

import com.example.CreditManagement.models.CurrentUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CurrentUsersRepositoryTest {
    @Autowired
    private CurrentUsersRepository underTest;

    @Test
    void itShouldFindUserByUsername() {

        //given
        String username = "user";
        String password = "user";

        CurrentUser currentUser = new CurrentUser();
        currentUser.setUsername(username);
        currentUser.setPassword(password);

        underTest.save(currentUser);

        //when
        Optional<CurrentUser> foundedUser = underTest.findByUsername(username);

        //then
        assertThat(foundedUser.isPresent()).isTrue();
        assertEquals(username, foundedUser.get().getUsername());
        assertEquals(password, foundedUser.get().getPassword());
    }
}