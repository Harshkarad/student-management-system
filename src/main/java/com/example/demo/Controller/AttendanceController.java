/*
 * package com.example.demo.Controller;
 * 
 * import com.example.demo.Service.Attendance_Service;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.*;
 * 
 * @RestController
 * 
 * @RequestMapping("/attendance") public class AttendanceController {
 * 
 * @Autowired private Attendance_Service attendanceService;
 * 
 * @PostMapping("/mark") public String markAttendance(@RequestParam Long
 * studentId,
 * 
 * @RequestParam Long teacherId,
 * 
 * @RequestParam String status) { return
 * attendanceService.markAttendance(studentId, teacherId, status); } }
 */