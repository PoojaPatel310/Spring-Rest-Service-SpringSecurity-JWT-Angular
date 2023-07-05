package com.example.springbootrestcontrollerangular.dao;

import com.example.springbootrestcontrollerangular.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Long> {

    Role findByName(String name);

}
