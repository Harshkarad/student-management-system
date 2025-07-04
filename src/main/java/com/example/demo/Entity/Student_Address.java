package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Address")
public class Student_Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long add_id;
	@Column
	private String city;
	@Column
	private String postalcode;
	@Column
	private String tehsil;
	@Column
	private String district;
	public long getAdd_id() {
		return add_id;
	}
	public void setAdd_id(long add_id) {
		this.add_id = add_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getTehsil() {
		return tehsil;
	}
	public void setTehsil(String tehsil) {
		this.tehsil = tehsil;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Student_Address(long add_id, String city, String postalcode, String tehsil, String district) {
		super();
		this.add_id = add_id;
		this.city = city;
		this.postalcode = postalcode;
		this.tehsil = tehsil;
		this.district = district;
	}
	public Student_Address() {
		super();
	}
	
	
	
}
