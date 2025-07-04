package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Student_personal_info;
import java.util.List;

@Repository
public interface Student_repository extends JpaRepository<Student_personal_info, Long>{
	boolean existsByEmail(String email);   // Check if email exists
    boolean existsByMobile(String mobile); // Check if mobile exists
    
    Optional<Student_personal_info> findByEmailAndPassword(String email, String password);
    Optional<Student_personal_info> findByMobileAndPassword(String mobile, String password);
    Optional<Student_personal_info> findByMobile(String mobile);
    Optional<Student_personal_info> findById(long id);
}
