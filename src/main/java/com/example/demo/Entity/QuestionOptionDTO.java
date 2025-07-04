package com.example.demo.Entity;

public class QuestionOptionDTO {
	 private String optionText;

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public QuestionOptionDTO(String optionText) {
		super();
		this.optionText = optionText;
	}

	public QuestionOptionDTO() {
		super();
	}
	 
}
