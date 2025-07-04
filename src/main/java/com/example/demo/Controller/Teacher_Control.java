package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//Add these imports
import com.example.demo.Entity.StudyMaterial;
import com.example.demo.Repository.Subject_Repository;
import com.example.demo.Repository.Test_Repository;
import com.example.demo.Entity.Announcement;
import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Grade;
import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.Test;
import com.example.demo.Repository.Announcement_Repository;
import com.example.demo.Repository.Attendance_Repository;
import com.example.demo.Repository.Grade_Repository;
import com.example.demo.Service.Admin_Service;
import com.example.demo.Service.Student_Service;
import com.example.demo.Service.Teacher_Service;

import java.util.ArrayList;
import java.util.Collections;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/teacher")
public class Teacher_Control {
	@Autowired
	private Grade_Repository grade_Repository;
	
	@Autowired
	private Test_Repository test_Repository;

	@Autowired
	private Admin_Service admin_Service;

	@Autowired
	private Teacher_Service teacherService;

	@Autowired
	private Student_Service student_Service;

	@Autowired
	private Announcement_Repository announcement_Repository;

	@Autowired
	private Attendance_Repository attendance_Repository;

	@Autowired
	private Subject_Repository subjectRepository;

	@Autowired
	private Teacher_Service teacher_Service;

	// @Autowired private Attendance_Service attendance_Service;

	/*
	 * @GetMapping("/register-teacher") public String RegisterTeacher() { return
	 * "register_teacher"; }
	 */

	/*
	 * @GetMapping("/admin") public String admin() { return "admin_home"; }
	 */

	@GetMapping("/teacher-login")
	public String TeacherLoginPage() {
		return "teacher_login";
	}

	@PostMapping("/teacher-login")
	private String loginTeacher(HttpSession session, @RequestParam("username") String mobile,
			@RequestParam("password") String password) {
		System.out.println(mobile);
		System.out.println(password);
		if (teacherService.verifyTeacher(mobile, password)) {
			session.setAttribute("username", mobile);

			Teacher teacher = teacherService.getTeacherInfo(mobile);

			if (teacher != null) {
				System.out.println("✅ Student found in login: " + teacher.getName());
				session.setAttribute("TeacherId", teacher.getId());
				session.setAttribute("TeacherName", teacher.getName());
				session.setAttribute("email", teacher.getEmail());
				session.setAttribute("Subject", teacher.getSubject());
				session.setAttribute("Department", teacher.getDepartment());
				session.setAttribute("gender", teacher.getGender());
				session.setAttribute("dateOfBirth", teacher.getDob());
				session.setAttribute("Experience", teacher.getExperience());
				session.setAttribute("city", teacher.getCity());
				session.setAttribute("State", teacher.getState());
				session.setAttribute("PinCode", teacher.getPincode());
				System.out.println("Until correct.");
				System.out.println(teacher.getId());
				System.out.println(teacher.getName());
				System.out.println(teacher.getDepartment());

				return "redirect:/teacher/teacher_home"; // Redirect to Teacher_Control's HomePage method

			}
		}
		return "teacher_login";
	}

	@GetMapping("/register-teacher")
	public String RegisterPage(Model model) {
		model.addAttribute("teacher", new Teacher());
		return "register_teacher";
	}
	/*
	 * @GetMapping("/teacher_home") public String Teacher_home() { return
	 * "teacher_home"; }
	 */

	@PostMapping("/register-teacher1")
	public String registerTeacher(@ModelAttribute Teacher teacher, Model model) {
		String result = teacherService.Register_Teacher(teacher);

		System.out.println(teacher.getAddress());
		System.out.println(teacher.getCity());
		System.out.println(teacher.getDob());
		System.out.println(teacher.getEmail());
		System.out.println(teacher.getMobile());
		System.out.println(teacher.getName());
		System.out.println(teacher.getGender());

		if (result.equals("Registered Successfully")) {
			model.addAttribute("message", "Teacher registered successfully!");

			return "login";
		}
		model.addAttribute("teacher", teacher);
		model.addAttribute("error", result);
		return "redirect:/teacher/register-teacher"; // Redirect to avoid duplicate form submission
	}

	@GetMapping("/teacher_home")
	public String HomePage(HttpSession session, Model model) {
	    String mobile = (String) session.getAttribute("username");

	    if (mobile == null) {
	        return "redirect:/";
	    }

	    // Rest of your existing data loading code...
	    long teacherId = (long) session.getAttribute("TeacherId");
	    String teacherName = (String) session.getAttribute("TeacherName");
	    String email = (String) session.getAttribute("email");
	    String department = (String) session.getAttribute("Department");

	    // Get today's date
	    String todayDate = LocalDate.now().toString();

	    List<Student_personal_info> students = student_Service.getAllStudents();

	    // Filter students who haven't had attendance marked today - using traditional loop instead of stream
	    List<Student_personal_info> students1 = new ArrayList<>();
	    if (students != null) {
	        for (Student_personal_info student : students) {
	            if (!attendance_Repository.existsByStudentIdAndTeacherIdAndDate(
	                    student.getId(), teacherId, todayDate)) {
	                students1.add(student);
	            }
	        }
	    }

	    // Add announcements to the model
	    List<Announcement> announcements = announcement_Repository.findCurrentAnnouncements();

	    List<Subjects> subjects = admin_Service.getAllSubjects();

	    model.addAttribute("teacherId", teacherId);
	    model.addAttribute("phone", mobile);
	    model.addAttribute("name", teacherName);
	    model.addAttribute("email", email);
	    model.addAttribute("subject", session.getAttribute("Subject"));
	    model.addAttribute("depasrtment", department);
	    model.addAttribute("students", students1);
	    model.addAttribute("announcements", announcements);
	    model.addAttribute("subjects", subjects);
	    return "teacher_home";
	}

	@PostMapping("/markAttendance")
	public String markAttendance(@RequestParam("studentId") long studentId, @RequestParam("teacherId") long teacherId,
			@RequestParam("status") String status, RedirectAttributes redirectAttributes) {
		try {
			// Your attendance marking logic here
			// For example:
			// attendance_Service.markAttendance(studentId, teacherId, status);

			String result = teacherService.markAttendance(studentId, teacherId, status);
			redirectAttributes.addFlashAttribute("successMessage", result);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/teacher/teacher_home";
	}

	@PostMapping("/uploadMaterial")
	public String uploadMaterial(@RequestParam String title, @RequestParam String description,
			@RequestParam String driveLink, @RequestParam long subjectId, @RequestParam Long teacherId,
			RedirectAttributes redirectAttributes) {

		try {
			Teacher teacher = teacher_Service.getTeacherById(teacherId);
			Subjects subject = subjectRepository.findById(subjectId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid subject ID"));

			StudyMaterial material = new StudyMaterial();
			material.setTitle(title);
			material.setDescription(description);
			material.setDriveLink(driveLink);
			material.setTeacher(teacher);
			material.setSubject(subject);

			teacher_Service.uploadMaterial(material);

			redirectAttributes.addFlashAttribute("successMessage", "Material uploaded successfully!");
			return "redirect:/teacher/teacher_home";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error uploading material: " + e.getMessage());
			return "redirect:/teacher/teacher_home";
		}

	}

	@GetMapping("/viewAttendance")
	public String StudentList(Model model) {
		List<Student_personal_info> studentlist = student_Service.getAllStudents();
		
		model.addAttribute("students", studentlist);
		return "Student_List";
	}

	@GetMapping("/download/{studentId}")
	public String generateStudentReport(@PathVariable Long studentId, Model model) {
	    try {
	        // 1. Get student information
	        Student_personal_info student = student_Service.getByStudentID(studentId)
	                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
	        
	        // 2. Get address details with null checks
	        String city = "";
	        String tehsil = "";
	        String districtString = "";
	        String postalString = "";
	        
	        if (student.getStudent_Address() != null) {
	            city = student.getStudent_Address().getCity() != null ? 
	                   student.getStudent_Address().getCity() : "";
	            tehsil = student.getStudent_Address().getTehsil() != null ? 
	                    student.getStudent_Address().getTehsil() : "";
	            districtString = student.getStudent_Address().getDistrict() != null ? 
	                           student.getStudent_Address().getDistrict() : "";
	            postalString = student.getStudent_Address().getPostalcode() != null ? 
	                         student.getStudent_Address().getPostalcode() : "";
	        }
	        
	        // 3. Get attendance records
	        List<Attendance> attendanceRecords = attendance_Repository.findAttendancesByStudentId(studentId);
	        
	        // Initialize the teacher-subject relationship for each record
	        if (attendanceRecords != null) {
	            for (Attendance attendance : attendanceRecords) {
	                if (attendance != null && attendance.getTeacher() != null) {
	                    Hibernate.initialize(attendance.getTeacher().getSubject());
	                }
	            }
	        }
	        
	        // 4. Calculate attendance stats using traditional loop instead of stream
	        int totalClasses = attendanceRecords != null ? attendanceRecords.size() : 0;
	        int presentCount = 0;
	        
	        if (attendanceRecords != null) {
	            for (Attendance attendance : attendanceRecords) {
	                if (attendance != null && "Present".equals(attendance.getStatus())) {
	                    presentCount++;
	                }
	            }
	        }
	        
	        int absentCount = totalClasses - presentCount;
	        
	        // 5. Get grade details for the student
	        List<Grade> grades = grade_Repository != null ? 
	                           grade_Repository.findByStudentId(studentId) : 
	                           Collections.emptyList();
	        
	        // 6. Add all attributes to model
	        model.addAttribute("city", city);
	        model.addAttribute("tehsil", tehsil);
	        model.addAttribute("districtString", districtString);
	        model.addAttribute("postalString", postalString);
	        model.addAttribute("student", student);
	        model.addAttribute("dob", student.getDob());
	        model.addAttribute("attendanceRecords", 
	                         attendanceRecords != null ? attendanceRecords : Collections.emptyList());
	        model.addAttribute("grades", grades);
	        
	        model.addAttribute("totalClasses", totalClasses);
	        model.addAttribute("presentCount", presentCount);
	        model.addAttribute("absentCount", absentCount);
	        
	        return "Student_Report";
	    } catch (Exception e) {
	        model.addAttribute("error", "Error generating report: " + e.getMessage());
	        return "error-page"; // Make sure you have an error page configured
	    }
	}
	
	@GetMapping("/profile")
	public String showTeacherProfile(HttpSession session, Model model) {
	    // Get teacher ID from session
	    Long teacherId = (Long) session.getAttribute("TeacherId");
	    
	    if (teacherId == null) {
	        return "redirect:/teacher/teacher-login";
	    }
	    
	    // Get teacher data from service
	    Teacher teacher = teacherService.getTeacherById(teacherId);
	    model.addAttribute("teacher", teacher);
	    
	    return "Edit-Teacher"; // This should match your Thymeleaf template name
	}

	@PostMapping("/profile/update")
	public String updateTeacherProfile(@ModelAttribute Teacher updatedTeacher, 
	                                HttpSession session,
	                                RedirectAttributes redirectAttributes) {
	    Long teacherId = (Long) session.getAttribute("TeacherId");
	    
	    if (teacherId == null) {
	        return "redirect:/teacher/teacher-login";
	    }
	    
	    // Get existing teacher data
	    Teacher existingTeacher = teacherService.getTeacherById(teacherId);
	    
	    // Update only the editable fields
	    existingTeacher.setName(updatedTeacher.getName());
	    existingTeacher.setEmail(updatedTeacher.getEmail());
	    existingTeacher.setMobile(updatedTeacher.getMobile());
	    existingTeacher.setAddress(updatedTeacher.getAddress());
	    existingTeacher.setCity(updatedTeacher.getCity());
	    existingTeacher.setState(updatedTeacher.getState());
	    existingTeacher.setPincode(updatedTeacher.getPincode());
	    existingTeacher.setQualification(updatedTeacher.getQualification());
	    existingTeacher.setExperience(updatedTeacher.getExperience());
	    
	    // Save the updated teacher
	    teacherService.updateTeacher(existingTeacher);
	    
	    // Update session attributes if needed
	    session.setAttribute("TeacherName", existingTeacher.getName());
	    session.setAttribute("email", existingTeacher.getEmail());
	    // Add other session updates as needed
	    
	    redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
	    return "redirect:/teacher/profile";
	}
}
