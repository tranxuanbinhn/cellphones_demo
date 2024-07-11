package com.cellphones10.service.impl;

import com.cellphones10.dto.UserDTO;
import com.cellphones10.entity.ERole;
import com.cellphones10.entity.Role;
import com.cellphones10.entity.User;
import com.cellphones10.repository.RoleRepository;
import com.cellphones10.repository.UserRepository;
import com.cellphones10.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder encoder;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ModelMapper mapper;
    @Override
    public UserDTO save(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername()))
        {
                throw new RuntimeException("User exist");
        }
        if(userRepository.existsByEmail(userDTO.getEmail()))
        {
            throw new RuntimeException("User exist");
        }
        if(userRepository.existsByPhonenumber(userDTO.getPhonenumber()))
        {
            throw new RuntimeException("User exist");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhonenumber(userDTO.getPhonenumber());
        String password = encoder.encode(userDTO.getPassword());
        user.setPassword(password);
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        if(userDTO.getRole().equals("user"))
        {
            role = roleRepository.findByName(ERole.ROLE_USER).get();

        }
        if(userDTO.getRole().equals("admin"))
        {
           role = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        }if(userDTO.getRole().equals("morderator"))
        {
            role = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
        }
        roles.add(role);
        user.setRoles(roles);
        User userResult = userRepository.save(user);
        UserDTO rs = mapper.map(userResult, UserDTO.class);
        rs.setPassword("");
        Role[] roles1 = user.getRoles().toArray(new Role[0]);
        switch (roles1[0].getName().toString())
        {
            case "ROLE_ADMIN":{
                rs.setRole("admin");
                break;
            }
            case "ROLE_USER":{
                rs.setRole("user");
                break;
            } case "ROLE_MODERATOR":{
            rs.setRole("moderator");
            break;
        }
        }


        return rs;
    }

    public Boolean update(UserDTO userDTO, Long id) {
       try {
           if(!userRepository.existsById(id))
           {
               throw new RuntimeException("User exist");
           }

           User user = new User();
           user = userRepository.findById(id).get();
           if(!user.getUsername().equals(userDTO.getUsername()))
           {
               user.setUsername(userDTO.getUsername());

           }
           if(!user.getEmail().equals(userDTO.getEmail()))
           {
               user.setEmail(userDTO.getEmail());

           }
           if(!user.getPhonenumber().equals(userDTO.getPhonenumber()))
           {
               user.setPhonenumber(userDTO.getPhonenumber());

           }

           Role[] roleList = user.getRoles().toArray(new Role[0]);
           String rolec = roleList[0].getName().toString();
           Set<Role> roles = new HashSet<>();
           Role role = new Role();
           if(rolec.equals("ROLE_ADMIN"))
           {
               rolec = "admin";
           }
           if(rolec.equals("ROLE_USER"))
           {
               rolec = "user";
           }if(rolec.equals("ROLE_MODERATOR"))
           {
               rolec = "moderator";
           }
           if(!userDTO.getRole().equals(rolec))
           {
               if(userDTO.getRole().equals("user"))
               {
                   role = roleRepository.findByName(ERole.ROLE_USER).get();

               }
               if(userDTO.getRole().equals("admin"))
               {
                   role = roleRepository.findByName(ERole.ROLE_ADMIN).get();
               }
               if(userDTO.getRole().equals("moderator"))
                {
                    role = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
                }
               roles.add(role);
               user.setRoles(roles);
           }


           userRepository.save(user);
           return true;
       }
       catch (RuntimeException e)
       {
           return false;
       }
    }
 public UserDTO findUserById(Long id)
 {
    try {
        User user = userRepository.findById(id).get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhonenumber(user.getPhonenumber());
        userDTO.setId(user.getId());
        Role[] roles1 = user.getRoles().toArray(new Role[0]);

        switch (roles1[0].getName().toString())
        {
            case "ROLE_ADMIN":{
                userDTO.setRole("admin");
                break;
            }
            case "ROLE_USER":{
                userDTO.setRole("user");
                break;
            } case "ROLE_MODERATOR":{
            userDTO.setRole("moderator");
            break;
        }}
        return  userDTO;
    }
    catch (RuntimeException e)
    {
        throw new RuntimeException("Not found");

    }
 }
    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userRepository.findAll(pageable).getContent();
        users.stream().forEach(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO = mapper.map(user, UserDTO.class);
            userDTO.setPassword("");
            Role[] roles1 = user.getRoles().toArray(new Role[0]);

            switch (roles1[0].getName().toString())
            {
                case "ROLE_ADMIN":{
                    userDTO.setRole("admin");
                    break;
                }
                case "ROLE_USER":{
                    userDTO.setRole("user");
                    break;
                } case "ROLE_MODERATOR":{
                userDTO.setRole("moderator");
                break;
            }}
            result.add(userDTO);
        });
        return result;
    }

    @Override
    public boolean delete(List<Long> list) {
        return false;
    }

    public Boolean resetPassword(String userName)
    {
        try{
            if(!userRepository.existsByUsername(userName))
            {
                throw new RuntimeException("User not found");
            }
            User user = userRepository.findByUsername(userName).get();
            String password = encoder.encode("123456789");

            user.setPassword(password);
            userRepository.save(user);
            return  true;
        }
        catch (RuntimeException e)
        {
            return  false;
        }

    }
    public Long count()
    {
        return userRepository.count();
    }

    public boolean delete(Long id) {
        try
        {


                userRepository.deleteById(id);

            return  true;
        }
        catch (RuntimeException e)
        {

            return false;
        }

    }
}
