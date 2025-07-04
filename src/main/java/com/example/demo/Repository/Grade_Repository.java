package com.example.demo.Repository;

import com.example.demo.Entity.Grade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Grade_Repository extends JpaRepository<Grade, Long> {
    // You can add custom queries here if needed
	List<Grade> findByStudentId(Long studentId);
}