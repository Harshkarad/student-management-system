package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Subjects;
@Repository
public interface Subject_Repository extends JpaRepository<Subjects, Long>{
	List<Subjects> findByTeacherId(Long teacherId);
	//List<Subjects> findById(int id);
	// In Subject_Repository
	@Query("SELECT s FROM Subjects s WHERE s.teacher.id = :teacherId")
	Optional<Subjects> findSingleByTeacherId(@Param("teacherId") Long teacherId);
}   
