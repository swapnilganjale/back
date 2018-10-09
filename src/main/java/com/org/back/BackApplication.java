package com.org.back;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

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
		storageService.deleteAll();
		storageService.init();
	}
}
