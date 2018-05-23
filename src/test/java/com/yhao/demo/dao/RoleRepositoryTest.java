package com.yhao.demo.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yhao.demo.entity.Role;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    
    @Test
    public void testInsert() {
        Role role = new Role();
        role.setName("管理员");
        role.setDescr("测试");
        Role result = this.roleRepository.save(role);
        System.out.println(result);
    }
    
    @Test
    public void testFindAll() {
        List<Role> list = this.roleRepository.findAll();
        for (Role role : list) {
        	System.out.println(role);
		}
    }
    
    @Test
    public void testUpdate() {
        Role role = new Role();
        role.setId(1);
        role.setName("管理员");
        role.setDescr("控制权限");
        Role result = this.roleRepository.save(role);
        System.out.println(result);
    }
    
    @Test
    public void testDelete() {
        this.roleRepository.deleteById(1);
    }
}