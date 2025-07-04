package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
public class Student_personal_info {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;
	@Column
	private int age;
	@Column
	private String dob;
	
	@Column
	private String gender;
	@Column
	private String nationality;
	@Column
	private String email;
	@Column
	private String mobile;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id",referencedColumnName = "add_id")
	private Student_Address student_Address;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "guardian_id",referencedColumnName = "guard_id")
	private Student_Guardian student_Guardian;
	@Column
	private String password;
	
	@PrePersist
	protected void onCreate() {
		this.admissionDateTime=LocalDateTime.now();
	}
	
	@Column(name = "Admission_Date")
	private LocalDateTime admissionDateTime;
	
	public Student_personal_info() {
		super();
	}
	public Student_personal_info(long id, String name, int age, String gender, String nationality, String email,
			String mobile, Student_Address student_Address, Student_Guardian student_Guardian, String password,String dob) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.nationality = nationality;
		this.email = email;
		this.mobile = mobile;
		this.student_Address = student_Address;
		this.student_Guardian = student_Guardian;
		this.password = password;
		this.dob=dob;
		this.admissionDateTime=LocalDateTime.now();
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Student_Address getStudent_Address() {
		return student_Address;
	}
	public void setStudent_Address(Student_Address student_Address) {
		this.student_Address = student_Address;
	}
	public Student_Guardian getStudent_Guardian() {
		return student_Guardian;
	}
	public void setStudent_Guardian(Student_Guardian student_Guardian) {
		this.student_Guardian = student_Guardian;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getAdmissionDateTime() {
		return admissionDateTime;
	}
	public void setAdmissionDateTime(LocalDateTime admissionDateTime) {
		this.admissionDateTime = admissionDateTime;
	}
	@Override
	public String toString() {
		return "Student_personal_info [id=" + id + ", name=" + name + ", age=" + age + ", dob=" + dob + ", gender="
				+ gender + ", nationality=" + nationality + ", email=" + email + ", mobile=" + mobile
				+ ", student_Address=" + student_Address + ", student_Guardian=" + student_Guardian + ", password="
				+ password + ", admissionDateTime=" + admissionDateTime + "]";
	}
	
	
	
}
