package com.yhao.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * Entity中不映射成列的字段得加@Transient注解，不加注解也会映射成列
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false, unique = true)
	private String userName;
	@Column(nullable = false, unique = true)
	private String passWord;
	@Column(nullable = false, unique = true)
	private String nickName;
	@Column(nullable = true, unique = false)
	private String email;

	public User() {
		super();
	}

	public User(Long id, String userName, String passWord, String nickName, String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.nickName = nickName;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
