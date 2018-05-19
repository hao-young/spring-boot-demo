#Spring Boot#

##Spring Boot热部署（springloader）##

*一、*

在`dependencies`中添加

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

*二、*
然后用run as -java application启动