package com.example.demo.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.QuestionOption;

import jakarta.transaction.Transactional;

public interface QuestionOption_Repository extends JpaRepository<QuestionOption	, Long> {
	 @Modifying
	    @Transactional
	    @Query("DELETE FROM QuestionOption qo WHERE qo.question.id IN " +
	           "(SELECT q.id FROM Question q WHERE q.test.id IN " +
	           "(SELECT t.id FROM Test t WHERE t.endTime <= :currentTime))")
	    void deleteOptionsOfExpiredTests(LocalDateTime currentTime);
}
