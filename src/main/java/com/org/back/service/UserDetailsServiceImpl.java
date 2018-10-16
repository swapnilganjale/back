package com.org.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.back.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername ==" + email);
		com.org.back.model.User user = new com.org.back.model.User();

		user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("email " + email + " not found");
		}
		List<GrantedAuthority> listOfRoles = new ArrayList<GrantedAuthority>();
		listOfRoles.add(new SimpleGrantedAuthority(user.getRole()));
		User dbuser= new User(user.getEmail(),user.getPassword(),listOfRoles);
		System.out.println("Final User == "+dbuser.toString());
		for (GrantedAuthority grantedAuthority : listOfRoles) {
			System.out.println("ROLE  == "+grantedAuthority.getAuthority());
		}
		return dbuser;
	}

}