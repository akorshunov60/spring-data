package com.example.spring_data.service;

import com.example.spring_data.model.entity.User;

public interface UserService {

//    User findById(Long id);

    User findByIdWithoutTransaction(Long id);

    void create(User user);

    void createWithoutTransaction(User user);
}
