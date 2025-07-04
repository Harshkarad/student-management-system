package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.Teacher;
import java.util.List;

@Repository
public interface Teacher_Repository extends JpaRepository<Teacher, Long>{
	Optional<Teacher> findByMobile(String mobile);
	boolean existsByEmail(String email);   // Check if email exists
    boolean existsByMobile(String mobile);
    
    Optional<Teacher> findByMobileAndPassword(String mobile, String password);

    Optional<Teacher> findById(Long id);
}
