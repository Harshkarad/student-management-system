package com.example.demo.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    
    private Long courseId;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationMinute;
    private Integer totalMarks;
    @Column(nullable = false)
    private Integer passingMarks;
    private String instruction;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subjects subject;
    
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
    
    // New field to track if test is active (for all students)
    @Column(columnDefinition = "boolean default true")
    private boolean active = true;
    
	// Constructors
    public Test() {
    }

    public Test(long id, String name, String description, Teacher teacher, Long courseId, 
               LocalDateTime startTime, LocalDateTime endTime, Integer durationMinute, 
               Integer totalMarks, String instruction, Subjects subject,boolean status,Integer passingMarks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinute = durationMinute;
        this.totalMarks = totalMarks;
        this.instruction = instruction;
        this.subject = subject;
        this.status=status;
        this.passingMarks=passingMarks;
    }

    
    
 // Getters and setters for all fields
    public Integer getPassingMarks() {
        return passingMarks;
    }
    
    public void setPassingMarks(Integer integer) {
        this.passingMarks = integer;
    }

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	// Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getDurationMinute() {
        return durationMinute;
    }

    public void setDurationMinute(Integer durationMinute) {
        this.durationMinute = durationMinute;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Test [id=" + id + ", name=" + name + ", description=" + description + ", teacher=" + teacher
                + ", courseId=" + courseId + ", startTime=" + startTime + ", endTime=" + endTime
                + ", durationMinute=" + durationMinute + ", totalMarks=" + totalMarks + ", instruction=" + instruction
                + ", subject=" + subject + "]";
    }
}