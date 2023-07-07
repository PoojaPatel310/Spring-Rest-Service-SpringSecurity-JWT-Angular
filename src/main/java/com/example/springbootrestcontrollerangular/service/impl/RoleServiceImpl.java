package com.example.springbootrestcontrollerangular.service.impl;

import com.example.springbootrestcontrollerangular.dao.RoleDao;
import com.example.springbootrestcontrollerangular.entity.Role;
import com.example.springbootrestcontrollerangular.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }
}
