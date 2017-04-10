package com.wb.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by wangbo on 2017/4/1.
 */
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@NotNull
	@Length(min = 3, max = 20)
	private String cell;
	
	@NotNull
	@Length(min = 3, max = 20)
	private String name;
	
	@NotNull
	@Length(min = 6, max = 40)
	private String password;
	
	private Role role;
	
	public String getPassword() {
		return password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCell() {
		return cell;
	}
	
	public void setCell(String cell) {
		this.cell = cell;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
