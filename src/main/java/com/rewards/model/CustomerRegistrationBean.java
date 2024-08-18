package com.rewards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="customer_registration")
public class CustomerRegistrationBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cust_id;
	
	@Column
	private String cust_name;
	
	@Column
	private String contact;
	
	@Column
	private String email;
	
	@Column
	private String user_name;
	
	@Column
	private String password;
	
	@Column
	private int confirm_password;

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(int confirm_password) {
		this.confirm_password = confirm_password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}