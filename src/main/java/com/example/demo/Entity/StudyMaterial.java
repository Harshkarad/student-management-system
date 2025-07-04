package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "study_materials")
public class StudyMaterial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    private String description;
    
    @Column(name = "drive_link", nullable = false)
    private String driveLink;
    
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subjects subject;
    
    // Constructors
    public StudyMaterial() {
        this.uploadDate = LocalDateTime.now();
    }
    
    public StudyMaterial(String title, String description, String driveLink, 
                        Teacher teacher, Subjects subject) {
        this();
        this.title = title;
        this.description = description;
        this.driveLink = driveLink;
        this.teacher = teacher;
        this.subject = subject;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDriveLink() {
        return driveLink;
    }
    
    public void setDriveLink(String driveLink) {
        this.driveLink = driveLink;
    }
    
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }
    
    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public Subjects getSubject() {
        return subject;
    }
    
    public void setSubject(Subjects subject) {
        this.subject = subject;
    }
}