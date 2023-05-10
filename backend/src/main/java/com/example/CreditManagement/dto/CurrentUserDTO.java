package com.example.CreditManagement.dto;

import com.example.CreditManagement.models.CurrentUser;

public class CurrentUserDTO {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static CurrentUser toEntity(CurrentUserDTO currentUserDTO){
        CurrentUser currentUser = new CurrentUser();

        currentUser.setId(currentUserDTO.getId());
        currentUser.setUsername(currentUserDTO.getUsername());
        currentUser.setPassword(currentUserDTO.getPassword());
        return currentUser;
    }

    public static CurrentUserDTO fromEntity(CurrentUser currentUser){
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        currentUserDTO.setId(currentUser.getId());
        currentUserDTO.setUsername(currentUser.getUsername());
        currentUserDTO.setPassword(currentUser.getPassword());
        return currentUserDTO;
    }
}
