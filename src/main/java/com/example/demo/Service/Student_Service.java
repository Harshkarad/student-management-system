package com.example.demo.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Student_Address;
import com.example.demo.Entity.Student_Guardian;
import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.StudyMaterial;
import com.example.demo.Entity.Subjects;
import com.example.demo.Repository.Attendance_Repository;
import com.example.demo.Repository.Student_repository;
import com.example.demo.Repository.StudyMaterial_Repository;
import com.example.demo.Repository.Subject_Repository;

@Service
public class Student_Service {
	
	
	@Autowired
	private Student_repository student_repository;

	@Autowired
	private Attendance attendance;

	@Autowired
	private Subject_Repository subject_Repository;

	@Autowired
	private Attendance_Repository attendance_Repository;

	@Autowired
	private StudyMaterial_Repository studyMaterial_Repository;
	
	public String registerStudent(Student_personal_info student) {
		String emailString = student.getEmail();
		String mobileString = student.getMobile();
		if (StudentExist(emailString, mobileString)) {
			student_repository.save(student); // Save only if email and mobile are unique
			return "Registered Successfully";
		}
		// student_repository.save(student); // Save only if email and mobile are unique
		return "Registered Failed!";
	}

	public boolean StudentExist(String email, String mobile) {
		if (student_repository.existsByEmail(email)) {
			return false;
		}
		if (student_repository.existsByMobile(mobile)) {
			return false;
		}
		return true;
	}

	public boolean verifyLogin(String username, String password) {
		if (username.contains("@")) {
			return student_repository.findByEmailAndPassword(username, password).isPresent();
		} else {
			return student_repository.findByMobileAndPassword(username, password).isPresent();
		}
	}

	public Student_personal_info getStudentByMobile(String mobile) {
		Optional<Student_personal_info> studentOptional = student_repository.findByMobile(mobile);

		if (studentOptional.isPresent()) {
			System.out.println("✅ Student found: " + studentOptional.get().getName());
			return studentOptional.get();
		} else {
			System.out.println("❌ No student found for mobile: " + mobile);
			return null;
		}
	}

	// New method to get all students
	public List<Student_personal_info> getAllStudents() {
		return student_repository.findAll();
	}

	public Student_personal_info getStudentById(Long id) {
		return student_repository.findById(id).orElse(null);
	}

	public List<Subjects> getall() {
		return subject_Repository.findAll();
	}

	public long getTotalPresentDays(Long studentId) {
		return attendance_Repository.countPresentByStudentId(studentId);
	}

	public long getTotalAbsentDays(Long studentId) {
		return attendance_Repository.countAbsentByStudentId(studentId);
	}

	// NEW: Method to handle filtered attendance queries

	// NEW: Get subjects by teacher ID
	public List<Subjects> getSubjectsByTeacher(Long teacherId) {
		return subject_Repository.findByTeacherId(teacherId);
	}

	public Optional<Subjects> SubjectFindById(long subid) {
		return subject_Repository.findById(subid);
	}

	public Optional<Attendance> FilterAttendence(long studentId,int SubjectId,String date) {
		return attendance_Repository.findByStudentIdAndTeacherSubjectIdAndDate(studentId, SubjectId, date);
	}
	
	public List<Attendance> FilterByStdidAndDate(long studentId,String date) {
		return attendance_Repository.findByStudentIdAndDate(studentId, date);
	}
	
	/*
	 * public List<StudyMaterial> getStudyMaterialsForStudent(Long studentId) { //
	 * First get the student's subjects Student_personal_info student =
	 * student_repository.findById(studentId).orElse(null); if (student == null) {
	 * return Collections.emptyList(); }
	 * 
	 * // In a real implementation, you might want to get materials based on the
	 * student's enrolled subjects // For now, we'll return all materials return
	 * studyMaterial_Repository.findAll(); }
	 */
	
	public List<StudyMaterial> getAllStudyMaterials() {
		return studyMaterial_Repository.findAll();
	}
	
	public Optional<Student_personal_info> getByStudentID(long stdID) {
		return student_repository.findById(stdID);
	}
	
	public void updateStudentProfile(
	        Long studentId,
	        String name, int age, String dob, String gender, String nation, String email, String mobile,
	        String city, String tehsil, String district, String postal,
	        String gname, String grelation, String gcontact) {
	    
	    // Get the student from repository
	    Optional<Student_personal_info> studentOpt = student_repository.findById(studentId);
	    
	    if (studentOpt.isPresent()) {
	        Student_personal_info student = studentOpt.get();
	        
	        // Update personal info
	        student.setName(name);
	        student.setAge(age);
	        student.setDob(dob);
	        student.setGender(gender);
	        student.setNationality(nation);
	        student.setEmail(email);
	        student.setMobile(mobile);
	        
	        // Update address
	        Student_Address address = student.getStudent_Address();
	        if (address == null) {
	            address = new Student_Address();
	            student.setStudent_Address(address);
	        }
	        address.setCity(city);
	        address.setTehsil(tehsil);
	        address.setDistrict(district);
	        address.setPostalcode(postal);
	        
	        // Update guardian info
	        Student_Guardian guardian = student.getStudent_Guardian();
	        if (guardian == null) {
	            guardian = new Student_Guardian();
	            student.setStudent_Guardian(guardian);
	        }
	        guardian.setName(gname);
	        guardian.setRelation(grelation);
	        guardian.setContact(gcontact);
	        
	        // Save the updated student
	        student_repository.save(student);
	    }
	}
}
