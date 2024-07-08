package com.cellphones10.service.impl;

import com.cellphones10.dto.RoleDTO;
import com.cellphones10.entity.Role;
import com.cellphones10.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired private RoleRepository roleRepository;
    public List<RoleDTO> findAllRole()
    {
        List<RoleDTO> roleDTOS = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        roles.stream().forEach(role -> {
        RoleDTO roleDTO = new RoleDTO();
        if(role.getName().name().equals("ROLE_ADMIN"))
        {
            roleDTO.setName("admin");
        }
        else if(role.getName().name().equals("ROLE_USER"))
        {
                roleDTO.setName("user");
        }
        else if(role.getName().name().equals("ROLE_MODERATOR"))
        {
                roleDTO.setName("moderator");
        }
            roleDTOS.add(roleDTO);
        });
        return  roleDTOS;
    }
}
