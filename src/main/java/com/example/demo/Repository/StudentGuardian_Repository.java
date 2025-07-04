package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Student_Guardian;

@Repository
public interface StudentGuardian_Repository extends JpaRepository<Student_Guardian, Long> {
}
