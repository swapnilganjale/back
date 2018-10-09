package com.org.back.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.back.dto.PageDTO;
import com.org.back.dto.PagingAndSortingRequest;
import com.org.back.model.User;
import com.org.back.repository.UserRepository;
import com.org.back.repository.opt.OptionalParameters;
import com.org.back.service.UserService;
import com.org.back.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	OptionalParameters<User> optionalParameters;

	@Override
	public User convertValue(String value, Class<User> clazz) {
		return null;
	}

	@Override
	public PageDTO<User> list(Map map) {
		PagingAndSortingRequest pagingAndSortingRequest = Utils.convertValue(map, PagingAndSortingRequest.class);
		System.out.println("pagingAndSortingRequest");
		System.err.println(pagingAndSortingRequest);
		PageDTO<User> users = optionalParameters.findWithOptionalParameters(pagingAndSortingRequest, User.class);
		return users;
	}
	
	@Override
	public PageDTO<User> search(Map map) {
		PagingAndSortingRequest pagingAndSortingRequest = Utils.convertValue(map, PagingAndSortingRequest.class);
 		PageDTO<User> users = optionalParameters.findWithSearchParameters(pagingAndSortingRequest, User.class);
		return users;
	}

	@Override
	public User create(User user) {
		return repository.save(user);
	}

	@Override
	public User delete(Integer id) {
		User user = findById(id);
		if (user != null) {
			repository.delete(user);
		}
		return user;
	}

	@Override
	public List findAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		return repository.findById(id).get();
	}

	@Override
	public User update(User user) {
		return repository.save(user);
	}

	

}