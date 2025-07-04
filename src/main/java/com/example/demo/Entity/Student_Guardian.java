package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Guardian")
public class Student_Guardian {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long guard_id;
	@Column(name = "Guardian_name")
	private String name;
	@Column
	private String relation;
	@Column
	private String contact;
	public Student_Guardian(long guard_id, String name, String relation, String contact) {
		super();
		this.guard_id = guard_id;
		this.name = name;
		this.relation = relation;
		this.contact = contact;
	}
	public Student_Guardian() {
		super();
	}
	public long getGuard_id() {
		return guard_id;
	}
	public void setGuard_id(long guard_id) {
		this.guard_id = guard_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}

