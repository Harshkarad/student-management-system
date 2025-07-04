package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Question;

import jakarta.transaction.Transactional;

public interface Question_Repository extends JpaRepository<Question, Long> {

	 @Modifying
	    @Transactional
	    @Query("DELETE FROM Question q WHERE q.test.id IN (SELECT t.id FROM Test t WHERE t.endTime <= :currentTime)")
	    void deleteQuestionsOfExpiredTests(LocalDateTime currentTime);

	 @Query("SELECT q FROM Question q LEFT JOIN FETCH q.options WHERE q.test.id = :testId")
	 List<Question> findByTestIdWithOptions(@Param("testId") Long testId);
}
