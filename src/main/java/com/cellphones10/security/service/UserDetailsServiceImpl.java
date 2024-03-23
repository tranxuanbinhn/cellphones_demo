package com.cellphones10.security.service;

import com.cellphones10.entity.User;
import com.cellphones10.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
    public String loadUsernameByEmail(String email) throws  UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        return user.getUsername();


}
    public String loadUsernameByPhonenumber(String phonenumber) throws  UsernameNotFoundException{
        User user = userRepository.findByPhonenumber(phonenumber);
        return user.getUsername();
    }

}
