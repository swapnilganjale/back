package com.org.back;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.org.back.model.User;
import com.org.back.repository.UserRepository;
import com.org.back.service.StorageService;

@SpringBootApplication
@EntityScan(basePackages = {"com.org.back.model"})   
public class BackApplication implements CommandLineRunner{
	
	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		System.out.println("Application Start ::");
		storageService.deleteAll();
		storageService.init();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encode;

	@PostConstruct
	public void init() {
		System.out.println("Init Method");

		User user = userRepository.findByEmail("admin");
	
		if (user == null) {
			user = defaultUser();
			userRepository.save(user);
		}
	}

	private User defaultUser() {
		User user = new User("admin", encode.encode("admin"),"USER");
		return user;
	}

}
