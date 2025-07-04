package com.example.demo.Repository;

import com.example.demo.Entity.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudyMaterial_Repository extends JpaRepository<StudyMaterial, Long> {
    List<StudyMaterial> findByTeacherId(Long teacherId);
    List<StudyMaterial> findBySubjectId(Long subjectId);
    
   
    List<StudyMaterial> findBySubjectId(Integer subjectId);
}