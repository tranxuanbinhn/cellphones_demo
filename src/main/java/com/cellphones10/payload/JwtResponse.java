package com.cellphones10.payload;

import java.util.List;

public class JwtResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private List<String> roles;
    private String accessToken;
    private String typeToken = "Bearer";

    public JwtResponse(String token, Long id, String username, String email,String phonenumber, List<String> roles) {
        this.accessToken = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTypeToken() {
        return typeToken;
    }

    public void setTypeToken(String typeToken) {
        this.typeToken = typeToken;
    }
}
