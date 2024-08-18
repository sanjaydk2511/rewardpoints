package com.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "reward_points")
public class RewardPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "cust_id") private CustomerRegistrationBean customer;
	 */

    @Column(name = "cust_id")
    private Long cust_id;
    
    @Column(name = "points")
    private Integer points;
    
    @Column(name="month")
    private int month;
    
    @Column(name="year")
    private int year;
    
    @Column(nullable = false, precision = 19, scale = 4, name="amount")
    private BigDecimal amount;
   

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getPoints() {
		return points;
	}

	public void setPoints(Object object) {
		this.points = (Integer) object;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

		@PrePersist
	    @PreUpdate
	    private void setCurrentMonthAndYear() {
	        LocalDate now = LocalDate.now();
	        this.month = now.getMonthValue();
	        this.year = now.getYear();
	    }
    
}