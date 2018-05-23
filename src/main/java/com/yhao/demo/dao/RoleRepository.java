package com.yhao.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yhao.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
