package com.yhao.demo.conf;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuffer sb = new StringBuffer();
				sb.append(target.getClass().getName())
				.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		RedisCacheManager rcm = RedisCacheManager.builder(factory).build();
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return rcm;
	}
	
	/**
	 * 防止redis入库序列化乱码的问题
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer()); //key序列化
	    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class)); //value序列化
	    template.afterPropertiesSet();
		return template;
	}
}
