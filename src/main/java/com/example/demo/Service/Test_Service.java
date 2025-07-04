package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Question;
import com.example.demo.Entity.QuestionDTO;
import com.example.demo.Entity.QuestionOption;
import com.example.demo.Entity.QuestionOptionDTO;
import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.Test;
import com.example.demo.Entity.TestFormDTO;
import com.example.demo.Repository.QuestionOption_Repository;
import com.example.demo.Repository.Question_Repository;
import com.example.demo.Repository.Subject_Repository;
import com.example.demo.Repository.Teacher_Repository;
import com.example.demo.Repository.Test_Repository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class Test_Service {
    @Autowired
    private Subject_Repository subject_Repository;
    
    @Autowired 
    private Question_Repository question_Repository;
    
    @Autowired
    private Test_Repository testRepository;

    @Autowired
    private Question_Repository questionRepository;

    @Autowired
    private QuestionOption_Repository questionOption_Repository;
    
    @Autowired
    private Teacher_Repository teacher_Repository;
    
    @Transactional
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    @Transactional
    public void createTest(TestFormDTO testForm, long teacherId) {
        System.out.println("===== START TEST CREATION DEBUG =====");
        System.out.println("Test Form Data:");
        System.out.println("Name: " + testForm.getName());
        System.out.println("Total Questions: " + (testForm.getQuestions() != null ? testForm.getQuestions().size() : 0));
        
        try {
            // 1. Validate and get teacher/subject
            Teacher teacher = teacher_Repository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + teacherId));
            
            Subjects subject = subject_Repository.findSingleByTeacherId(teacherId)
                    .orElseThrow(() -> new RuntimeException("No subject assigned to teacher ID: " + teacherId));

            // 2. Create and save Test
            Test test = new Test();
            test.setName(testForm.getName());
            test.setDescription(testForm.getDescription());
            test.setTeacher(teacher);
            test.setSubject(subject);
            test.setStartTime(testForm.getStartTime());
            test.setEndTime(testForm.getEndTime());
            test.setDurationMinute(testForm.getDurationMinute());
            test.setTotalMarks(testForm.getTotalMarks());
            test.setPassingMarks(testForm.getPassingMarks());
            test.setInstruction(testForm.getInstruction());
            
            Test savedTest = testRepository.save(test);
            System.out.println("Test saved with ID: " + savedTest.getId());

            // 3. Process Questions
            if (testForm.getQuestions() == null || testForm.getQuestions().isEmpty()) {
                System.out.println("WARNING: No questions provided in test form");
                return;
            }

            System.out.println("Processing " + testForm.getQuestions().size() + " questions...");
            for (int i = 0; i < testForm.getQuestions().size(); i++) {
                QuestionDTO questionDTO = testForm.getQuestions().get(i);
                System.out.println("\nQuestion #" + (i + 1) + " Details:");
                System.out.println("Text: " + questionDTO.getQuestionText());
                System.out.println("Type: " + questionDTO.getQuestionType());
                System.out.println("Marks: " + questionDTO.getMarks());
                System.out.println("Options Count: " + (questionDTO.getOptions() != null ? questionDTO.getOptions().size() : 0));
                System.out.println("Correct Options: " + (questionDTO.getCorrectOptions() != null ? questionDTO.getCorrectOptions().toString() : "null"));

                Question question = new Question();
                question.setTest(savedTest);
                question.setQuestionText(questionDTO.getQuestionText());
                question.setQuestionType(questionDTO.getQuestionType());
                question.setMarks(questionDTO.getMarks());
                
                Question savedQuestion = questionRepository.save(question);
                System.out.println("Saved Question ID: " + savedQuestion.getId());

                // Process Options if MCQ
                if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
                    System.out.println("Processing options for Question #" + (i + 1));
                    for (int j = 0; j < questionDTO.getOptions().size(); j++) {
                        QuestionOptionDTO optionDTO = questionDTO.getOptions().get(j);
                        System.out.println("Option #" + (j + 1) + ": " + optionDTO.getOptionText());
                        
                        QuestionOption option = new QuestionOption();
                        option.setQuestion(savedQuestion);
                        option.setOptionText(optionDTO.getOptionText());
                        
                        // Determine if this option is correct
                        boolean isCorrect = questionDTO.getCorrectOptions() != null && 
                                          questionDTO.getCorrectOptions().contains(j);
                        option.setCorrect(isCorrect);
                        System.out.println("Is correct: " + isCorrect);
                        
                        questionOption_Repository.save(option);
                    }
                }
            }
            System.out.println("===== TEST CREATION COMPLETED SUCCESSFULLY =====");
        } catch (Exception e) {
            System.err.println("ERROR in createTest: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create test: " + e.getMessage(), e);
        }
    }
    
    public Optional<Teacher> GetTeacherInfo(long teacherId) {
        System.out.println("Fetching teacher info for ID: " + teacherId);
        return teacher_Repository.findById(teacherId);
    }
     
    public List<Subjects> SubjectList(long TeacherID) {
        System.out.println("Fetching subjects for teacher ID: " + TeacherID);
        return subject_Repository.findByTeacherId(TeacherID);
    }

	/*
	 * @Transactional public void deleteExpiredTests() { LocalDateTime now =
	 * LocalDateTime.now(); System.out.println("Deleting tests expired before: " +
	 * now); int deletedCount = testRepository.deleteExpiredTests(now);
	 * System.out.println("Deleted " + deletedCount + " expired tests"); }
	 * 
	 * @Scheduled(fixedRate = 3600000) // Runs every hour public void
	 * cleanupExpiredTests() {
	 * System.out.println("Running scheduled test cleanup at: " +
	 * LocalDateTime.now()); deleteExpiredTests(); }
	 */
    
    public List<Test> ListOfTest() {
        System.out.println("Fetching all tests");
        return testRepository.findAll();
    } 
}