package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Entity.Announcement;
import com.example.demo.Entity.Attendance;
import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Teacher;
import com.example.demo.Repository.Announcement_Repository;
import com.example.demo.Repository.Attendance_Repository;
import com.example.demo.Service.Admin_Service;
import com.example.demo.Service.Student_Service;
import com.example.demo.Service.Teacher_Service;

@Controller
@RequestMapping("/admin")
public class Admin_Control {
	@Autowired
	private Attendance_Repository attendance_Repository;
	
	@Autowired
	private Admin_Service admin_Service;

	@Autowired
	private Announcement_Repository announcement_Repository;

	@Autowired
	private Teacher_Service teacher_Service;

	@Autowired
	private Student_Service student_Service;

	@GetMapping("/admin-home")
	public String adminHome(Model model) {

		loadAdminData(model);
		return "admin_home";
	}

	@GetMapping("/admin-login")
	public String AdminLoginPage() {
		return "admin_login";
	}

	@PostMapping("/admin-login")
	public String AdminLogin(Model model, @RequestParam("username") String mobile,
			@RequestParam("password") String password) {
		if (mobile.equals("1112") && password.equals("11")) {
			loadAdminData(model);
			// Load all announcements (not just current ones) for admin view
			List<Announcement> announcements = announcement_Repository.findAllByOrderByPostDateDesc();
			model.addAttribute("announcements", announcements);
			System.out.println(announcements);
			return "admin_home";
		}
		return "admin_login";
	}

	@PostMapping("/addSubject")
	public String SaveSubject(@ModelAttribute Subjects subjects, @RequestParam("teacherId") long teacherid,
			RedirectAttributes redirectAttributes) {

		Teacher teacher = teacher_Service.getTeacherById(teacherid);
		if (teacher == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error: Teacher not found");
			return "redirect:/login"; // or wherever your admin page is
		}

		try {
			admin_Service.addSubject(subjects, teacherid);
			redirectAttributes.addFlashAttribute("successMessage1", "Subject added successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
		}

		return "redirect:/admin/admin-home";
	}

	private void loadAdminData(Model model) {
		List<Teacher> teachers = teacher_Service.getAllTeachersWithSubjects();
		model.addAttribute("teachers", teachers);
		model.addAttribute("subjects", new Subjects());

		List<Subjects> subject1 = admin_Service.getAllSubjects();
		model.addAttribute("subjects1", subject1);

		List<Teacher> TeacherWithSubject = teacher_Service.getAllTeachersWithSubjects();
		model.addAttribute("subjectwithteacher", TeacherWithSubject);

		List<Student_personal_info> student = student_Service.getAllStudents();
		model.addAttribute("students", student);
	}

	/*
	 * @PostMapping("addTeacher") public String
	 * Add_Teacher_ByAdmin(@RequestParam("teacherName") String T_name,
	 * 
	 * @RequestParam("teacherMobile") String t_mobile,
	 * 
	 * @RequestParam("teacherPassword") String password, Model model) { if
	 * (teacher_Service.verifyTeacher(t_mobile, password)) { return
	 * "Teacher with this mobile already exists!"; }
	 * 
	 * Teacher teacher= new Teacher(T_name,t_mobile,password);
	 * 
	 * teacher.setName(T_name); teacher.setMobile(t_mobile);
	 * teacher.setPassword(password);
	 * 
	 * model.addAttribute("successMessage", "Teacher added successfully!");
	 * 
	 * admin_Service.Add_Teacher(teacher); return "admin_home"; }
	 */

	@PostMapping("/removeTeacher")
	public String DeleteTeacher(@RequestParam("teacherId") long id, RedirectAttributes redirectAttributes) {
		teacher_Service.DeleteTeacherById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Teacher deleted successfully");
		return "redirect:/admin/admin-home"; // Changed to redirect to admin-home
	}

	@PostMapping("/assignSubject")
	public String AssignSubject(@RequestParam("teacherId") long teach_id, @RequestParam("subjectId") int sub_id,
			RedirectAttributes redirectAttributes) {
		try {
			String result = admin_Service.AssignSubject(teach_id, sub_id);
			redirectAttributes.addFlashAttribute("successMessage", result);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
		}
		return "redirect:/admin/admin-home";
	}

	@PostMapping("/createAnnouncement")
	public String createAnnouncement(@RequestParam String title, @RequestParam String content,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate,
			RedirectAttributes redirectAttributes) {

		Announcement announcement = new Announcement();
		announcement.setTitle(title);
		announcement.setContent(content);
		announcement.setExpiryDate(expiryDate);

		announcement_Repository.save(announcement);

		redirectAttributes.addFlashAttribute("successMessage", "Announcement posted successfully!");
		return "redirect:/admin/admin-home";
	}

	@PostMapping("/deleteAnnouncement")
	public String deleteAnnouncement(@RequestParam Long announcementId, RedirectAttributes redirectAttributes) {
		announcement_Repository.deleteById(announcementId);
		redirectAttributes.addFlashAttribute("successMessage", "Announcement deleted successfully!");
		return "redirect:/admin/admin-home";
	}
	
	
	
	@GetMapping("/generateAttendanceReport")
	public String Report(@RequestParam("startDate") String Startdate,
						 @RequestParam("endDate") String Enddate,
						 @RequestParam("studentId") Long studentId,
						 @RequestParam("subjectId") Integer subjectID,
						 @RequestParam("format") String Format,
						 Model model) {
		System.out.println(Startdate);
		System.out.println(Enddate);
		System.out.println(studentId);
		System.out.println(subjectID);
		System.out.println(Format);
		
		Student_personal_info nameString=student_Service.getStudentById(studentId);
		System.out.println(nameString.getName());
		
		
		
		return "redirect:/admin/admin-home";
	}

	@GetMapping("/filterAttendance")
	public String AttendenceReport(@RequestParam("date") String date,
	                             @RequestParam("subjectId") int subjectId,
	                             Model model) {
	    
	    // Get attendance records based on date and subject
	    List<Attendance> attendances = attendance_Repository.findBySubjectIdAndDate(subjectId, date);
	    
	    // Add to model
	    model.addAttribute("attendanceRecords", attendances);
	    
	    // Reload other admin data
	    loadAdminData(model);
	    
	    return "admin_home";
	}
}