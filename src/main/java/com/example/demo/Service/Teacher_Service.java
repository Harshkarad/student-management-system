package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Teacher;
import com.example.demo.Repository.Attendance_Repository;
import com.example.demo.Repository.StudyMaterial_Repository;
import com.example.demo.Repository.Subject_Repository;
import com.example.demo.Repository.Teacher_Repository;
//Add these imports at the top
import com.example.demo.Entity.StudyMaterial;
import com.example.demo.Repository.StudyMaterial_Repository;
import jakarta.transaction.Transactional;
@Service
public class Teacher_Service {
	@Autowired
	private Teacher_Repository teacher_Repository;
	@Autowired
	private Subject_Repository subject_Repository;
	@Autowired
	private Attendance_Repository attendance_Repository;
	
	// Add this autowired repository
	@Autowired
	private StudyMaterial_Repository studyMaterial_Repository;
	
	//Register New Teacher
	public String Register_Teacher(Teacher teacher) {
        String mobile = teacher.getMobile();
        String email=teacher.getEmail();
       
        if (!TeacherExist(email, mobile)) {
        	return "Registered Failed!";
        }
        
        System.out.println("Saving teacher to database...");
        teacher_Repository.save(teacher);
        System.out.println("Teacher saved successfully!");
        return "Registered Successfully";
    }
	
	//Check if teacher is already exist
	public boolean TeacherExist(String email, String mobile) {
	    System.out.println("Checking if teacher exists...");
	    if (teacher_Repository.existsByEmail(email)) {
	        System.out.println("Email already exists: " + email);
	        return false;
	    } 
	    if (teacher_Repository.existsByMobile(mobile)) {
	        System.out.println("Mobile already exists: " + mobile);
	        return false;
	    }
	    System.out.println("Teacher does not exist, proceeding with registration.");
	    return true;
	}

	//Verify teacher through username(Mobile) and password
	public boolean verifyTeacher(String username,String password) {
		return teacher_Repository.findByMobileAndPassword(username, password).isPresent();
	}
	
	//Get Teacher Information store in db
	public Teacher getTeacherInfo(String mobile) {
		Optional<Teacher> teacherOptional=teacher_Repository.findByMobile(mobile);
		
		if (teacherOptional.isPresent()) {
			System.out.println("Teacher Foiund:"+teacherOptional.get().getName());
			return teacherOptional.get();
		} else {
			System.out.println("No Teacher Found.");
			return null;
		}
	}
	
	//Get teacher with respective to subject
	public List<Teacher> getAllTeachersWithSubjects() {
        return teacher_Repository.findAll();
    }
	 // Define the missing method
    public Teacher getTeacherById(long teacherId) {
        Optional<Teacher> optionalTeacher = teacher_Repository.findById(teacherId);
        return optionalTeacher.orElse(null); // Returns teacher if found, otherwise null
    }
    
    //Save teacher
    public void saveTeacher(Teacher teacher) {
        teacher_Repository.save(teacher);
       
    }
    
    // Delete Teacher By Id
    @Transactional
    public void DeleteTeacherById(long id) {
        Teacher teacher = teacher_Repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        teacher_Repository.delete(teacher);
    }
    
    public String markAttendance(long studentId, long teacherId, String status) {
        try {
            // Get current date
            String todayDate = LocalDate.now().toString();
            
            // Check if attendance already exists for this student today
            Optional<Attendance> existingAttendance = attendance_Repository
                .findByStudentIdAndTeacherIdAndDate(studentId, teacherId, todayDate);
            
            if (existingAttendance.isPresent()) {
                // Update existing record
                Attendance attendance = existingAttendance.get();
                attendance.setStatus(status);
                attendance_Repository.save(attendance);
                return "Attendance updated successfully!";
            } else {
                // Create new attendance record
                Attendance attendance = new Attendance();
                
                // Create and set student
                Student_personal_info student = new Student_personal_info();
                student.setId(studentId);
                attendance.setStudent(student);
                
                // Create and set teacher
                Teacher teacher = new Teacher();
                teacher.setId(teacherId);
                attendance.setTeacher(teacher);
                
                attendance.setStatus(status);
                attendance.setDate(todayDate);
                
                attendance_Repository.save(attendance);
                return "Attendance marked successfully!";
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark attendance: " + e.getMessage());
        }
    }
    
 // Add these new methods to the service class
    public List<StudyMaterial> getMaterialsByTeacher(Long teacherId) {
        return studyMaterial_Repository.findByTeacherId(teacherId);
    }
    
    public StudyMaterial uploadMaterial(StudyMaterial material) {
        return studyMaterial_Repository.save(material);
    }

    public List<StudyMaterial> getMaterialsBySubject(Long subjectId) {
        return studyMaterial_Repository.findBySubjectId(subjectId);
    }
    
    //Return List of attendence of a student
	/*
	 * public List<Attendance> getAttendances(long StudentId) { return
	 * attendance_Repository.findByStudentId1(StudentId); }
	 */
    public void updateTeacher(Teacher teacher) {
        teacher_Repository.save(teacher);
    }
}
