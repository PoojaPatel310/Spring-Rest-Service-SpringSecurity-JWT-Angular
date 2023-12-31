package com.example.springbootrestcontrollerangular.service;

import com.example.springbootrestcontrollerangular.entity.User;

public interface UserService {

    User loadUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);
}
