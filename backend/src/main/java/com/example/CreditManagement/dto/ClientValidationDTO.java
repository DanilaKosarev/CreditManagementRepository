package com.example.CreditManagement.dto;

public class ClientValidationDTO {
    private boolean isEmailOccupied;
    private boolean isPassportNumberOccupied;

    public boolean isEmailOccupied() {
        return isEmailOccupied;
    }

    public void setEmailOccupied(boolean emailOccupied) {
        isEmailOccupied = emailOccupied;
    }

    public boolean isPassportNumberOccupied() {
        return isPassportNumberOccupied;
    }

    public void setPassportNumberOccupied(boolean passportNumberOccupied) {
        isPassportNumberOccupied = passportNumberOccupied;
    }
}
