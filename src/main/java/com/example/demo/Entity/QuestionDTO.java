package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
	 private String questionText;
	    private String questionType;
	    private Double marks;
	    private List<QuestionOptionDTO> options= new ArrayList<>();
	    private List<Integer> correctOptions = new ArrayList<>();   // Initialize here
		public String getQuestionText() {
			return questionText;
		}
		public void setQuestionText(String questionText) {
			this.questionText = questionText;
		}
		public String getQuestionType() {
			return questionType;
		}
		public void setQuestionType(String questionType) {
			this.questionType = questionType;
		}
		public Double getMarks() {
			return marks;
		}
		public void setMarks(Double marks) {
			this.marks = marks;
		}
		public List<QuestionOptionDTO> getOptions() {
			return options;
		}
		public void setOptions(List<QuestionOptionDTO> options) {
			this.options = options;
		}
		public List<Integer> getCorrectOptions() {
			return correctOptions;
		}
		public void setCorrectOptions(List<Integer> correctOptions) {
			this.correctOptions = correctOptions;
		}
		public QuestionDTO(String questionText, String questionType, Double marks, List<QuestionOptionDTO> options,
				List<Integer> correctOptions) {
			super();
			this.questionText = questionText;
			this.questionType = questionType;
			this.marks = marks;
			this.options = options;
			this.correctOptions = correctOptions;
		}
		public QuestionDTO() {
			super();
		}
		@Override
		public String toString() {
			return "QuestionDTO [questionText=" + questionText + ", questionType=" + questionType + ", marks=" + marks
					+ ", options=" + options + ", correctOptions=" + correctOptions + "]";
		}
	    
}
