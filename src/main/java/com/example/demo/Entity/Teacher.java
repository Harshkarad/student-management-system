package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    private String name;
    
    private String email;
    
    private String mobile;
    private String subject;
    private String department;
    private int experience;
    private String gender;
    private String qualification;
    private String dob;
    private String address;
    private String city;
    private String state;
    private String pincode;
    
    private String password;
    
    
    
 // In Teacher entity
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subjects> subjects = new ArrayList<>();
    
 // Helper method to maintain consistency
    public void addSubject(Subjects subject) {
        subjects.add(subject);
        subject.setTeacher(this);
    }

    
    public List<Subjects> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subjects> subjects) {
		this.subjects = subjects;
	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // One teacher can have multiple students (For Attendance)
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Attendance> attendanceRecords;

    // ✅ Default Constructor
    public Teacher() {
    }

    // ✅ Constructor with fields
    public Teacher(String password,String pincode,String state,String city,String address,String dob,String name, String email, String mobile, String subject, String department,int experiance,String gender,String qualification) {
        this.password=password;
    	this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.subject = subject;
        this.department = department;
        this.experience=experiance;
        this.gender=gender;
        this.qualification=qualification;
        this.pincode=pincode;
        this.state=state;
        this.city=city;
        this.address=address;
        this.dob=dob;
        this.createdAt = LocalDateTime.now(); // Automatically set when created
    }
    
    public Teacher(String name,String mobile,String password) {
		this.name=name;
		this.mobile=mobile;
		this.password=password;
	}

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(List<Attendance> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	} 
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	// ✅ toString() Method (Optional - Useful for Debugging)
    @Override
    public String toString() {
        return "Teacher [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile 
                + ", subject=" + subject + ", department=" + department + ",experiance="+experience+",gender="+gender+",qualification="+qualification+"]";
    }
}
