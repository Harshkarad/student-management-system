package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Attendance;

@Repository
public interface Attendance_Repository extends JpaRepository<Attendance, Long> {
	boolean existsByStudentIdAndTeacherIdAndDate(long studentId, long teacherId, String date);

	Optional<Attendance> findByStudentIdAndTeacherIdAndDate(long studentId, long teacherId, String date);

	Optional<Attendance> findByStudentId(Long studentId);
	
	@Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId")
	List<Attendance> findAttendancesByStudentId(Long studentId);

	@Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'Present'")
	long countPresentByStudentId(@Param("studentId") Long studentId);

	@Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'Absent'")
	long countAbsentByStudentId(@Param("studentId") Long studentId);

	// NEW: Find attendance by teacher's subjects and date
    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.teacher.id IN " +
           "(SELECT t.id FROM Teacher t JOIN t.subjects s WHERE s.id = :subjectId) AND a.date = :date")
    Optional<Attendance> findByStudentIdAndTeacherSubjectIdAndDate(
            @Param("studentId") Long studentId,
            @Param("subjectId") Integer subjectId,
            @Param("date") String date);

 // NEW: Find attendance by date only
    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.date = :date")
    List<Attendance> findByStudentIdAndDate(
            @Param("studentId") Long studentId,
            @Param("date") String date);
    
 // NEW: Find attendance by teacher's subjects only
    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.teacher.id IN " +
           "(SELECT t.id FROM Teacher t JOIN t.subjects s WHERE s.id = :subjectId)")
    Optional<Attendance> findByStudentIdAndTeacherSubjectId(
            @Param("studentId") Long studentId,
            @Param("subjectId") Integer subjectId);
    
    
 
    @Query("SELECT a FROM Attendance a JOIN a.teacher t JOIN t.subjects s " +
    	       "WHERE s.id = :subjectId AND a.date = :date")
    	List<Attendance> findBySubjectIdAndDate(
    	        @Param("subjectId") Integer subjectId,
    	        @Param("date") String date);
    //List<Attendance> findByStudentId(Long studentId);

}
