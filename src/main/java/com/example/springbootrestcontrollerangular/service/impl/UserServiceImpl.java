package com.example.springbootrestcontrollerangular.service.impl;

import com.example.springbootrestcontrollerangular.dao.RoleDao;
import com.example.springbootrestcontrollerangular.dao.UserDao;
import com.example.springbootrestcontrollerangular.entity.Role;
import com.example.springbootrestcontrollerangular.entity.User;
import com.example.springbootrestcontrollerangular.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

   private UserDao userDao;
   private RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User loadUserByEmail(String email) {
       return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userDao.save(new User(email,password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleDao.findByName(roleName);
        user.assignRoleToUser(role);

    }
}
