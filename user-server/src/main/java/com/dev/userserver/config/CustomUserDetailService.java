package com.dev.userserver.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dev.userserver.model.User;
import com.dev.userserver.repository.UserRepo;

@Configuration
public class CustomUserDetailService implements UserDetailsService{
  
  @Autowired
  private UserRepo userRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    Optional<User> user = userRepository.findByEmail(username);
    return user.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Does Not Exist"));
  }
}
