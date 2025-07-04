package com.example.demo.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Test;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface Test_Repository extends JpaRepository<Test, Long> {
	Optional<Test> findById(long id);
	
	@EntityGraph(attributePaths = {"questions", "questions.options"})
    @Query("SELECT t FROM Test t WHERE t.id = :testId")
    Optional<Test> findByIdWithQuestions(@Param("testId") Long testId);
	
	@Query("DELETE FROM Test t WHERE t.endTime <= :currentTime")
    int deleteExpiredTests(LocalDateTime currentTime);
	
	@Query("SELECT t FROM Test t WHERE NOT EXISTS " +
	           "(SELECT g FROM Grade g WHERE g.test = t AND g.student.id = :studentId)")
	    List<Test> findUnattemptedTestsByStudentId(@Param("studentId") Long studentId);
	
	@Query("SELECT t FROM Test t WHERE EXISTS " +
		       "(SELECT g FROM Grade g WHERE g.test = t AND g.student.id = :studentId)")
		List<Test> findAttemptedTestsByStudentId(@Param("studentId") Long studentId);
}
