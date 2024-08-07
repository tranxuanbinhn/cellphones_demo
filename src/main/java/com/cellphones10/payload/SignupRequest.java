package com.cellphones10.payload;

import java.util.Set;

public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String phonenumber;


    public SignupRequest(String userName, String email, String password,String phonenumber, Set<String> role) {
        this.username = userName;
        this.email = email;
        this.password = password;

        this.phonenumber = phonenumber;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
