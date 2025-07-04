package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Grade;
import com.example.demo.Entity.Question;
import com.example.demo.Entity.QuestionDTO;
import com.example.demo.Entity.QuestionOption;
import com.example.demo.Entity.Student_personal_info;
import com.example.demo.Entity.Subjects;
import com.example.demo.Entity.Test;
import com.example.demo.Entity.TestFormDTO;
import com.example.demo.Repository.Grade_Repository;
import com.example.demo.Repository.Question_Repository;
import com.example.demo.Repository.Test_Repository;
import com.example.demo.Service.Test_Service;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private Grade_Repository grade_Repository;
	
    @Autowired
    private Test_Repository test_Repository;
    
    @Autowired
    private Question_Repository question_Repository;
    
    @Autowired
    private Test_Service test_Service;

    @GetMapping("/create")
    public String OrganizeTest(HttpSession session, Model model) {
        long teacherId = (long) session.getAttribute("TeacherId");
        List<Subjects> subjects = test_Service.SubjectList(teacherId);

        TestFormDTO testForm = new TestFormDTO();
        model.addAttribute("testForm", testForm);
        model.addAttribute("courses", subjects);
        return "create-test";
    }

    @PostMapping("/create")
    public String createTest(@ModelAttribute("testForm") TestFormDTO testForm, 
                            BindingResult result,
                            HttpSession session, 
                            Model model) {
        
        // Debug output
        System.out.println("===== TEST CREATION DEBUG START =====");
        System.out.println("Number of questions in form: " + (testForm.getQuestions() != null ? testForm.getQuestions().size() : 0));
        
        if (testForm.getQuestions() != null) {
            for (int i = 0; i < testForm.getQuestions().size(); i++) {
                QuestionDTO q = testForm.getQuestions().get(i);
                System.out.println("Question " + i + ": " + q.getQuestionText());
                if (q.getOptions() != null) {
                    System.out.println("Options count: " + q.getOptions().size());
                    for (int j = 0; j < q.getOptions().size(); j++) {
                        System.out.println("  Option " + j + ": " + q.getOptions().get(j).getOptionText());
                    }
                }
                if (q.getCorrectOptions() != null) {
                    System.out.println("Correct options: " + q.getCorrectOptions());
                }
            }
        }
        
        if (result.hasErrors()) {
            System.out.println("Form errors: " + result.getAllErrors());
            long teacherId = (long) session.getAttribute("TeacherId");
            model.addAttribute("courses", test_Service.SubjectList(teacherId));
            return "create-test";
        }

        try {
            long teacherId = (long) session.getAttribute("TeacherId");
            test_Service.createTest(testForm, teacherId);
            return "redirect:/teacher/teacher_home";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error: " + e.getMessage());
            long teacherId = (long) session.getAttribute("TeacherId");
            model.addAttribute("courses", test_Service.SubjectList(teacherId));
            return "create-test";
        }
    }

    @GetMapping("/take-test/{testId}")
    public String viewTest(@PathVariable long testId, Model model, HttpSession session, 
                          HttpServletResponse response) {
        try {
            // Check if test was already submitted
            if (session.getAttribute("testSubmitted_" + testId) != null) {
                return "redirect:/home?error=Test already submitted";
            }

            // Set cache control headers
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            System.out.println("===== STARTING TEST VIEW DEBUGGING =====");
            
            // Debug test retrieval
            System.out.println("Fetching test with ID: " + testId);
            Test test = test_Repository.findById(testId)
                .orElseThrow(() -> new Exception("Test not found or access denied"));
            System.out.println("Found test: " + test.getName());
            
            // Debug timing check
            System.out.println("Current time: " + LocalDateTime.now());
            System.out.println("Test start time: " + test.getStartTime());
            if (test.getStartTime().isAfter(LocalDateTime.now())) {
                throw new Exception("Test not available yet");
            }
            
            // Debug questions loading
            System.out.println("Loading questions for test...");
            List<Question> questions = question_Repository.findByTestIdWithOptions(testId);
            System.out.println("Found " + questions.size() + " questions");
            
            // Debug each question and its options
            for (Question question : questions) {
                System.out.println("Question: " + question.getQuestionText());
                System.out.println("Type: " + question.getQuestionType());
                System.out.println("Options count: " + (question.getOptions() != null ? question.getOptions().size() : "null"));
                
                if (question.getOptions() != null) {
                    for (QuestionOption option : question.getOptions()) {
                        System.out.println("  Option: " + option.getOptionText() + 
                                         " (Correct: " + option.isCorrect() + ")");
                    }
                }
            }
            
            test.setQuestions(questions);
            model.addAttribute("tests", Collections.singletonList(test));
            
            System.out.println("===== DEBUGGING COMPLETE =====");
            return "test-view";
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "test-error";
        }
    }
    
    @PostMapping("/submit-test")
    public String SubmitTest(
        @RequestParam("testId") long testId,
        @RequestParam Map<String, String> allParams,
        HttpSession session,
        Model model) {
        
        try {
            System.out.println("===== STARTING TEST SUBMISSION PROCESS =====");
            
            // Get student from session
            Long studentId = (Long) session.getAttribute("studentId");
            if (studentId == null) {
                throw new Exception("Student not logged in");
            }
            
            // Get test
            Test test = test_Repository.findById(testId)
                .orElseThrow(() -> new Exception("Test not found"));
            
         // Add null checks
            if (test.getTotalMarks() == null || test.getPassingMarks() == null) {
                throw new Exception("Test configuration is incomplete");
            }
            
            // Calculate score
            double totalMarks = test.getTotalMarks();
            double passingMarks = test.getPassingMarks();
            double marksObtained = 0;
            
            List<Question> questions = question_Repository.findByTestIdWithOptions(testId);
            
            for (Question question : questions) {
                String paramName = "question_" + question.getId();
                String studentAnswer = allParams.get(paramName);
                
                if (studentAnswer != null && !studentAnswer.isEmpty()) {
                    if (question.getQuestionType().equalsIgnoreCase("MCQ") ||
                        question.getQuestionType().equalsIgnoreCase("MCQ_SINGLE")) {
                        
                        for (QuestionOption option : question.getOptions()) {
                            if (option.getId().toString().equals(studentAnswer) && option.isCorrect()) {
                                marksObtained += question.getMarks();
                                break;
                            }
                        }
                    } 
                    else if (question.getQuestionType().equalsIgnoreCase("True/False")) {
                        for (QuestionOption option : question.getOptions()) {
                            if (option.isCorrect() && option.getOptionText().equalsIgnoreCase(studentAnswer)) {
                                marksObtained += question.getMarks();
                                break;
                            }
                        }
                    }
                }
            }
            
            // Determine pass/fail status
            boolean isPassed = marksObtained >= passingMarks;
            
            // Update test status
            test.setStatus(isPassed); // Assuming status is a boolean field in Test entity
            test_Repository.save(test);
            
            // Save grade (without status, since it's in Test now)
            Student_personal_info student = new Student_personal_info();
            student.setId(studentId);
            
            Grade grade = new Grade(
                student,
                test,
                test.getSubject(),
                marksObtained,
                totalMarks
                // No status parameter here anymore
            );
            
            grade_Repository.save(grade);
            
            // Mark test as submitted in session
            session.setAttribute("testSubmitted_" + testId, true);
            session.setAttribute("successMessage", "Test submitted successfully! " + 
                (isPassed ? "You passed!" : "You didn't pass this time."));
            
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Submission failed: " + e.getMessage());
            return "test-error";
        }
    }
}