package com.cellphones10.security.service;

import com.cellphones10.repository.RoleRepository;
import com.cellphones10.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


}
