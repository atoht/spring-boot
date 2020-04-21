package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//import com.example.demo.mapper.Registered;

@Service("authUserService")
//@Component
public class AuthUserService implements UserDetailsService {

//	@Autowired
//	private Registered registered;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		com.example.demo.model.User userModel = registered.selectByName(username);
//		List<GrantedAuthority> auths = new ArrayList<>();
//		auths.add(new SimpleGrantedAuthority("USER"));
		User user = new User(username, new BCryptPasswordEncoder().encode("aaaaaaaa"), AuthorityUtils.commaSeparatedStringToAuthorityList("USER, ROLE_USER"));
//		return User.withUsername("user").password(encoder.encode("password")).roles("USER").build();
		return user;
	}

}
