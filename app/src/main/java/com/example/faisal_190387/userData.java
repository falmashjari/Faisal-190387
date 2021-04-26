package com.example.faisal_190387;

public class userData {
    String FirstName,Email,LastName,Phone;

    public userData() {
    }

    public userData(String firstName, String email, String lastName, String phone) {
        FirstName = firstName;
        Email = email;
        LastName = lastName;
        Phone = phone;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
