package com.example.demo.Entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;

@Entity
@Component
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student_personal_info student;

    private String status; // Present or Absent
    private String date;   // Format: YYYY-MM-DD

    // ✅ Default Constructor
    public Attendance() {
    }

    // ✅ Constructor with fields
    public Attendance(Teacher teacher, Student_personal_info student, String status, String date) {
        this.teacher = teacher;
        this.student = student;
        this.status = status;
        this.date = date;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student_personal_info getStudent() {
        return student;
    }

    public void setStudent(Student_personal_info student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // ✅ toString() Method (Optional for Debugging)
    @Override
    public String toString() {
        return "Attendance [id=" + id + ", teacher=" + teacher.getName() + ", student=" + student.getName()
                + ", status=" + status + ", date=" + date + "]";
    }
}
