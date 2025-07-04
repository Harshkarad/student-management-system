package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student_personal_info student;
    
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subjects subject;
    
    private Double marksObtained;
    private Double totalMarks;
    private LocalDateTime submissionTime;
    
    // Constructors, getters, and setters
    public Grade() {
    }

    public Grade(Student_personal_info student, Test test, Subjects subject, 
                Double marksObtained, Double totalMarks) {
        this.student = student;
        this.test = test;
        this.subject = subject;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.submissionTime = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student_personal_info getStudent() {
        return student;
    }

    public void setStudent(Student_personal_info student) {
        this.student = student;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public Double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

	@Override
	public String toString() {
		return "Grade [id=" + id + ", student=" + student + ", test=" + test + ", subject=" + subject
				+ ", marksObtained=" + marksObtained + ", totalMarks=" + totalMarks + ", submissionTime="
				+ submissionTime + "]";
	}
    
    
}