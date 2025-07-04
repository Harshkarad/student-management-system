package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Teacher;
import com.example.demo.Repository.Subject_Repository;
import com.example.demo.Repository.Teacher_Repository;

import jakarta.transaction.Transactional;

@Service
public class Admin_Service {
	

	  @Autowired 
	  private Subject_Repository subject_Repository;
	 
	  @Autowired
	  private Teacher_Repository teacher_Repository;
	
	// Add Subject
	    public void addSubject(Subjects subjects,Long teacherId) {
	    	Optional<Teacher> teacherOptional=teacher_Repository.findById(teacherId);
	    	
	    	if (!teacherOptional.isPresent()) {
				throw new RuntimeException("Teacher not found");
			}
	    	Teacher teacher=teacherOptional.get(); // Extracting teacher
	    	subjects.setTeacher(teacher);   // Setting teacher before saving
	    	teacher.setSubject(subjects.getName());
	    	subject_Repository.save(subjects);
	    }
	    
	   public List<Subjects> getAllSubjects() {
		return subject_Repository.findAll();
	}
	   
	//Add Teacher
	   public Teacher Add_Teacher(Teacher teacher) {
		return teacher_Repository.save(teacher);
	}
	   
	@Transactional
	public String AssignSubject(long teacher_id,long subject_id) {
		// 1. Get fresh instances of both entities
	    Teacher teacher = teacher_Repository.findById(teacher_id)
	        .orElseThrow(() -> new RuntimeException("Teacher not found"));
	    Subjects subject = subject_Repository.findById(subject_id)
	        .orElseThrow(() -> new RuntimeException("Subject not found"));
		
	 // 2. Clear previous assignment if exists
	    if (subject.getTeacher() != null) {
	        Teacher previousTeacher = subject.getTeacher();
	        previousTeacher.getSubjects().remove(subject);
	        teacher_Repository.save(previousTeacher);
	    }
		
	 // 3. Set new relationship
	    subject.setTeacher(teacher);
	    teacher.getSubjects().add(subject);
	    
	    // 4. Update teacher's subject field
	    teacher.setSubject(subject.getName());

	    // 5. Save changes
	    subject_Repository.save(subject);
	    teacher_Repository.save(teacher);
	    
	    System.out.println("Attempting to assign:");
	    System.out.println("Teacher ID: " + teacher_id + ", Subject ID: " + subject_id);
	    System.out.println("Subject current teacher: " + 
	        (subject.getTeacher() != null ? subject.getTeacher().getId() : "null"));
	    
	    return "Subject '" + subject.getName() + "' assigned to teacher '" + teacher.getName() + "' successfully!";
	}
	   
}
