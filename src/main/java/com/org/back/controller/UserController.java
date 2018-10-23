package com.org.back.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.back.dto.PageDTO;
import com.org.back.model.User;
import com.org.back.service.UserService;

@CrossOrigin
@RestController
@RequestMapping({ "/user" })
@PreAuthorize("hasAuthority('ADMIN_USER')")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = { "/test" })
	public String test() {

		return "user";
	}

	@PostMapping
	public User create(@RequestBody User user) {
		return userService.create(user);
	}

	@GetMapping(path = { "/{id}" })
	public User findOne(@PathVariable("id") int id) {
		return userService.findById(id);
	}

	@PutMapping
	public User update(@RequestBody User user) {
		return userService.update(user);
	}

	@DeleteMapping(path = { "/{id}" })
	public User delete(@PathVariable("id") int id) {
		return userService.delete(id);
	}

	@GetMapping
	public List findAll() {
		return userService.findAll();
	}

	@PostMapping(path = { "/list" })
	public PageDTO<User> list(@RequestBody Map map) {
		System.out.println("list call");
		System.out.println(map);
		return userService.list(map);
	}

	@PostMapping(path = { "/search" })
	public PageDTO<User> search(@RequestBody Map map) {
		System.out.println("search call");
		System.out.println(map);
		return userService.search(map);
	}
}
