package com.example.mydatabaseapplication;

public class User {

    private int cod;
    private String name, email, cellPhone;

    public User(int cod, String name, String email, String cellPhone) {
        this.cod = cod;
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public User(String name, String email, String cellPhone) {
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public User() {
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}
