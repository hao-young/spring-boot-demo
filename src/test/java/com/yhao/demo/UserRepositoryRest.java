package com.yhao.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yhao.demo.dao.UserRepository;
import com.yhao.demo.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryRest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() throws Exception {
		userRepository.save(new User((long) 1024, "142", "215", "2215", "4452"));
		userRepository.save(new User((long) 1025, "152", "255", "2265", "452"));
		
		Assert.assertEquals(4, userRepository.findAll().size());
		userRepository.delete(userRepository.findByUserName("142"));
	}
}
