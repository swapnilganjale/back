package com.org.back.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.org.back.dto.PageDTO;
import com.org.back.model.User;

public interface UserService {

    User create(User user);

    User delete(Integer id);

    List<User> findAll();

    User findById(Integer id);

    User update(User user);

	PageDTO<User> list(Map map);

	User convertValue(String value, Class<User> clazz);

	PageDTO<User> search(Map map);
}