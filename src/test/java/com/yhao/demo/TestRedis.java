package com.yhao.demo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.yhao.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		stringRedisTemplate.opsForValue().set("aaa", "111");
		System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
	}

	@Test
	public void testObj() throws Exception {
		User user = new User(1234, "aa", "aa122345", new Date());
		ValueOperations<String, User> operations = redisTemplate.opsForValue();
		operations.set("com.yhao", user);
		operations.set("com.yhao.f", user, 1, TimeUnit.SECONDS);
		Thread.sleep(1000);
		// redisTemplate.delete("com.yhao.f");
		boolean exists = redisTemplate.hasKey("com.yhao.f");
		if (exists) {
			System.out.println("exists is true");
		} else {
			System.out.println("exists is false");
		}
	}
}
