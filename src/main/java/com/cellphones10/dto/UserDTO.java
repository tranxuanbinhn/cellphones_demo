package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDTO extends AbstractDTO{
    private  String username;
    private  String email;
    private  String password;
    private  String phonenumber;
    private List<Long> orderIds;
    private String role;

    public UserDTO(String username, String email, String password, String phonenumber, List<Long> orderIds) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
        this.orderIds = orderIds;
    }

    public UserDTO() {

    }
}
