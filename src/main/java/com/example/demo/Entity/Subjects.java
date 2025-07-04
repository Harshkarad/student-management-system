package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private int semester;

    @Column(nullable = false)
    private int totalMarks;

    @Column(nullable = false)
    private int passMarks;

    // Many subjects can be assigned to one teacher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id",nullable = true)
    private Teacher teacher;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<StudyMaterial> studyMaterials = new ArrayList<>();
    
    // Constructors
    public Subjects() {}

    public Subjects(String name, String code, int semester,
                   int totalMarks, int passMarks, Teacher teacher) {
        this.name = name;
        this.code = code;
        
        this.semester = semester;
        this.totalMarks = totalMarks;
        this.passMarks = passMarks;
        this.teacher = teacher;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

   
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public int getPassMarks() { return passMarks; }
    public void setPassMarks(int passMarks) { this.passMarks = passMarks; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

	@Override
	public String toString() {
		return "Subjects [id=" + id + ", name=" + name + ", code=" + code + ", semester=" + semester + ", totalMarks="
				+ totalMarks + ", passMarks=" + passMarks + ", teacher=" + teacher + "]";
	}

    
}
