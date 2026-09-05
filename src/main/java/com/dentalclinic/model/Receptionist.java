package com.dentalclinic.model;

public class Receptionist {

    private int receptionistId;
    private String username;
    private String password;
    private String name;
    private String contactNumber;
    private String email;

    public Receptionist() {
    }

    public Receptionist(int receptionistId, String username, String password, String name, String contactNumber, String email) {
        this.receptionistId = receptionistId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public int getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(int receptionistId) {
        this.receptionistId = receptionistId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
