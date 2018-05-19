# Spring Boot

## Spring Boot热部署（springloader）

** 一、**  
在 `dependencies` 中添加

```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<optional>true</optional>
	</dependency>
```

在`build`下的`plugins`标签下添加plugin:

```xml
	<plugin>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-maven-plugin </artifactId>
		<configuration>
			<fork>true</fork>
		</configuration>
	</plugin>
```

** 二、**  
然后用run as -java application启动

##自定义Filter##

1、实现Filter接口，实现Filter方法
2、添加 `@Configuration` 注解，将自定义Filter加入过滤链 