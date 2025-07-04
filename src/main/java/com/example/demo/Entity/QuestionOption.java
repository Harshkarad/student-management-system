package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String optionText;
    private boolean isCorrect;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "option_id") private Test test;
	 */
    
    // Constructors
    public QuestionOption() {
    }

    public QuestionOption(Long id, String optionText, boolean isCorrect, Question question) {
        this.id = id;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.question = question;
        
    }

    
    
    

	// Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionOption [id=" + id + ", optionText=" + optionText + ", isCorrect=" + isCorrect + "]";
    }
}