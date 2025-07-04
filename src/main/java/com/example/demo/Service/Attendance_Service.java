/*
 * package com.example.demo.Service;
 * 
 * import com.example.demo.Entity.Attendance; import
 * com.example.demo.Entity.Student_personal_info; import
 * com.example.demo.Entity.Teacher; import
 * com.example.demo.Repository.Attendance_Repository; import
 * com.example.demo.Repository.Student_repository; import
 * com.example.demo.Repository.Teacher_Repository;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import java.time.LocalDate; import java.util.Optional;
 * 
 * @Service public class Attendance_Service {
 * 
 * @Autowired private Attendance_Repository attendanceRepository;
 * 
 * @Autowired private Student_repository studentRepository;
 * 
 * @Autowired private Teacher_Repository teacherRepository;
 * 
 * public String markAttendance(Long studentId, Long teacherId, String status) {
 * String todayDate = LocalDate.now().toString();
 * 
 * Optional<Student_personal_info> studentOpt =
 * studentRepository.findById(studentId); Optional<Teacher> teacherOpt =
 * teacherRepository.findById(teacherId);
 * 
 * if (studentOpt.isEmpty() || teacherOpt.isEmpty()) { return
 * "Invalid Student or Teacher ID"; }
 * 
 * Student_personal_info student = studentOpt.get(); Teacher teacher =
 * teacherOpt.get();
 * 
 * Optional<Attendance> existingAttendance =
 * attendanceRepository.findByStudentAndDate(student, todayDate); if
 * (existingAttendance.isPresent()) { return "Attendance already marked for " +
 * student.getName() + " on " + todayDate; }
 * 
 * Attendance attendance = new Attendance(teacher, student, status, todayDate);
 * attendanceRepository.save(attendance);
 * 
 * return "Attendance marked successfully for " + student.getName(); } }
 */