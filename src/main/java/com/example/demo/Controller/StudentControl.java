package com.example.demo.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//Add these imports
import com.example.demo.Entity.StudyMaterial;
import com.example.demo.Repository.StudyMaterial_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Announcement;
import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Grade;
import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Test;
import com.example.demo.Repository.Announcement_Repository;
import com.example.demo.Repository.Attendance_Repository;
import com.example.demo.Repository.Grade_Repository;
import com.example.demo.Repository.Subject_Repository;
import com.example.demo.Repository.Test_Repository;
import com.example.demo.Service.Admin_Service;
import com.example.demo.Service.Student_Service;
import com.example.demo.Service.Teacher_Service;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentControl {
	@Autowired
	private Grade_Repository grade_Repository;
	
	@Autowired
	private Test_Repository test_Repository;
	
	@Autowired
	private Student_Service student_Service;

	@Autowired
	private Teacher_Service teacher_Service;

	@Autowired
	private Admin_Service admin_Service;

	@Autowired
	private Attendance_Repository attendance_Repository;

	@Autowired
	private Subject_Repository subject_Repository;
	
	@Autowired
	private Announcement_Repository announcement_Repository;

	@Autowired
	private StudyMaterial_Repository studyMaterial_Repository;
	
	@GetMapping("/")
	public String loginPage() {

		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") String mobile, @RequestParam("password") String password, Model model,
			HttpSession session) {
		System.out.println("🔍 Login attempt for mobile: " + mobile);

		if (student_Service.verifyLogin(mobile, password)) {
			session.setAttribute("username", mobile);

			// Fetch student data using mobile
			Student_personal_info student = student_Service.getStudentByMobile(mobile);

			if (student != null) {
				session.setAttribute("username", mobile);

				System.out.println("✅ Student found in login: " + student.getName());
				session.setAttribute("studentId", student.getId());
				session.setAttribute("studentName", student.getName());
				session.setAttribute("email", student.getEmail());
				session.setAttribute("phone", student.getMobile());
				session.setAttribute("nation", student.getNationality());
				session.setAttribute("gender", student.getGender());
				session.setAttribute("dateOfBirth", student.getDob());
				session.setAttribute("Age", student.getAge());
				session.setAttribute("city", student.getStudent_Address().getCity());
				session.setAttribute("tehsil", student.getStudent_Address().getTehsil());
				session.setAttribute("district", student.getStudent_Address().getDistrict());
				session.setAttribute("post", student.getStudent_Address().getPostalcode());
				session.setAttribute("gname", student.getStudent_Guardian().getName());
				session.setAttribute("grelation", student.getStudent_Guardian().getRelation());
				session.setAttribute("gcontact", student.getStudent_Guardian().getContact());

			}

			else {
				System.out.println("❌ Student is null in login!");
			}

			return "redirect:/home";
		}

		System.out.println("❌ Invalid login for mobile: " + mobile);
		model.addAttribute("message", "Invalid Mobile Number Or Password.");
		return "login";

	}

	@GetMapping("/register")
	public String RegisterPage(Model model) {
		model.addAttribute("student", new Student_personal_info());
		return "register";
	}

	@PostMapping("/register")
	public String RegisterStudent(@ModelAttribute Student_personal_info student, Model model) {
		// student_Service.registerStudent(student); // Save student details
		String messageString = student_Service.registerStudent(student);

		if (messageString.equals("Registered Successfully")) {
			model.addAttribute("message", "Registered Successfully!");
			return "login"; // Redirect to login page after successful registration
		}
		model.addAttribute("student", student);
		model.addAttribute("message", "User Already Registered");
		return "register";

	}

	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		String mobile = (String) session.getAttribute("username");

		if (mobile == null) {
			return "redirect:/"; // Redirect to login if not logged in
		}

		 // Get student ID
	    Long studentId = (Long) session.getAttribute("studentId");
		
		List<Announcement> announcements = announcement_Repository.findCurrentAnnouncements();
		model.addAttribute("announcements", announcements);
		
		// Get only unattempted tests
	    List<Test> tests = test_Repository.findUnattemptedTestsByStudentId(studentId);
	 // Get attempted tests with grades
	    List<Test> attemptedTests = test_Repository.findAttemptedTestsByStudentId(studentId);
	    
	    // 5. Get grade details for the student
        List<Grade> grades = grade_Repository != null ? 
                           grade_Repository.findByStudentId(studentId) : 
                           Collections.emptyList();
     // Create map without Streams
        Map<Long, Grade> gradeMap = new HashMap<>();
        for (Grade grade : grades) {
            if (grade != null && grade.getTest() != null) {
                gradeMap.put(grade.getTest().getId(), grade);
            }
        }
        model.addAttribute("attempted", attemptedTests);
        model.addAttribute("testGrades", gradeMap);
	    model.addAttribute("tests", tests);
	    //model.addAttribute("testGrades", grades);
		System.out.println("Test:"+tests);
		model.addAttribute("tests", tests);
		
		
		// Get study materials for student's subjects
		/*
		 * List<StudyMaterial> materials =
		 * studyMaterial_Repository.findByStudentId(studentId);
		 * model.addAttribute("materials", materials);
		 */ 
		List<StudyMaterial> materials=student_Service.getAllStudyMaterials();
		model.addAttribute("materials", materials);
		
		//model.addAttribute("studentId", session.getAttribute("studentId"));
		// long studentid=(long) session.getAttribute("studentId");
		// session.setAttribute("Studentid", studentid);

		model.addAttribute("studentName", session.getAttribute("studentName"));
		model.addAttribute("email", session.getAttribute("email"));
		model.addAttribute("phone", session.getAttribute("phone"));
		model.addAttribute("gender", session.getAttribute("gender"));
		model.addAttribute("dateOfBirth", session.getAttribute("dateOfBirth"));

		// Check for success message from test submission
	    if (session.getAttribute("successMessage") != null) {
	        model.addAttribute("successMessage", session.getAttribute("successMessage"));
	        session.removeAttribute("successMessage");
	    }
		
		return "home"; // Show home page
	}

	@GetMapping("/profile")
	public String Profile(HttpSession session, Model model) {
		String mobile = (String) session.getAttribute("username");
		if (mobile == null) {
			return "redirect:/"; // Redirect to login if not logged in
		}
		model.addAttribute("name", session.getAttribute("studentName"));
		model.addAttribute("age", session.getAttribute("Age"));
		model.addAttribute("dob", session.getAttribute("dateOfBirth"));
		model.addAttribute("gender", session.getAttribute("gender"));
		model.addAttribute("nation", session.getAttribute("nation"));
		model.addAttribute("email", session.getAttribute("email"));
		model.addAttribute("mobile", session.getAttribute("phone"));
		model.addAttribute("city", session.getAttribute("city"));
		model.addAttribute("tehsil", session.getAttribute("tehsil"));
		model.addAttribute("district", session.getAttribute("district"));
		model.addAttribute("postal", session.getAttribute("post"));
		model.addAttribute("gname", session.getAttribute("gname"));
		model.addAttribute("grelation", session.getAttribute("grelation"));
		model.addAttribute("gcontact", session.getAttribute("gcontact"));
		return "Student_profile";
	}
	
	
	@PostMapping("/update-profile")
	public String updateProfile(
	        @RequestParam String name,
	        @RequestParam int age,
	        @RequestParam String dob,
	        @RequestParam String gender,
	        @RequestParam String nation,
	        @RequestParam String email,
	        @RequestParam String mobile,
	        @RequestParam String city,
	        @RequestParam String tehsil,
	        @RequestParam String district,
	        @RequestParam String postal,
	        @RequestParam String gname,
	        @RequestParam String grelation,
	        @RequestParam String gcontact,
	        HttpSession session) {
	    
	    // Get student ID from session
	    Long studentId = (Long) session.getAttribute("studentId");
	    
	    if (studentId == null) {
	        return "redirect:/"; // Redirect to login if not logged in
	    }
	    
	    // Update the student profile
	    student_Service.updateStudentProfile(
	        studentId,
	        name, age, dob, gender, nation, email, mobile,
	        city, tehsil, district, postal,
	        gname, grelation, gcontact
	    );
	    
	    // Update session attributes with new values
	    session.setAttribute("studentName", name);
	    session.setAttribute("Age", age);
	    session.setAttribute("dateOfBirth", dob);
	    session.setAttribute("gender", gender);
	    session.setAttribute("nation", nation);
	    session.setAttribute("email", email);
	    session.setAttribute("phone", mobile);
	    session.setAttribute("city", city);
	    session.setAttribute("tehsil", tehsil);
	    session.setAttribute("district", district);
	    session.setAttribute("post", postal);
	    session.setAttribute("gname", gname);
	    session.setAttribute("grelation", grelation);
	    session.setAttribute("gcontact", gcontact);
	    
	    return "redirect:/profile";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // Destroy the session
		return "redirect:/"; // Redirect to the login page
	}

	@GetMapping("/attendance")
	public String ViewAttendence(HttpSession session, Model model, @RequestParam(required = false) String date,
			@RequestParam(required = false) Integer subjectId) {

		String studentName = (String) session.getAttribute("studentName");
		long studentId=(long) session.getAttribute("studentId");
		//System.out.println(studentId);
		//System.out.println(studentName);
		
		long PresentDays=student_Service.getTotalPresentDays(studentId);
		long AbsentDays=student_Service.getTotalAbsentDays(studentId);
		
		long TotalDays=PresentDays+AbsentDays;
		
		List<Subjects> subjects=student_Service.getall();
		
		//System.out.println(subjects);
		
		model.addAttribute("StudentName", studentName);
		model.addAttribute("StudentId", studentId);
		model.addAttribute("Present", PresentDays);
		model.addAttribute("Absent", AbsentDays);
		model.addAttribute("TotalDays", TotalDays);
		model.addAttribute("Subjects", subjects);
		return "attendence";
	}

	@GetMapping("/filter-attendance")
	public String attendenceFilter(@RequestParam("date") String date, 
	                             @RequestParam(required = false) Integer subjectId,
	                             HttpSession session, Model model) {
	    long studentId = (long) session.getAttribute("studentId");
	    String studentName = (String) session.getAttribute("studentName");
	    
	    System.out.println("Subject id is:"+subjectId);
	    
	    if (subjectId == null) {
	        // Get all attendance records for this date
	        List<Attendance> attendanceList = student_Service.FilterByStdidAndDate(studentId, date);
	        model.addAttribute("attendanceRecords", attendanceList);
	    } else {
	        // Get attendance for specific subject and date
	        Optional<Attendance> filteredData = student_Service.FilterAttendence(studentId, subjectId, date);
	        if (filteredData.isPresent()) {
	            model.addAttribute("filteredAttendance", filteredData.get());
	        }
	    }
	    
	    // Add all the existing attributes needed for the page
	    long PresentDays = student_Service.getTotalPresentDays(studentId);
	    long AbsentDays = student_Service.getTotalAbsentDays(studentId);
	    long TotalDays = PresentDays + AbsentDays;
	    
	    model.addAttribute("StudentName", studentName);
	    model.addAttribute("StudentId", studentId);
	    model.addAttribute("Present", PresentDays);
	    model.addAttribute("Absent", AbsentDays);
	    model.addAttribute("TotalDays", TotalDays);
	    model.addAttribute("Subjects", student_Service.getall());
	    model.addAttribute("selectedDate", date);
	    model.addAttribute("selectedSubjectId", subjectId);
	    
	    return "attendence";
	}
	
	
}
