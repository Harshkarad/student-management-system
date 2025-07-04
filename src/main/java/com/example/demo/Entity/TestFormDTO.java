package com.example.demo.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import com.example.demo.Entity.QuestionDTO;
public class TestFormDTO {
	private String name;
    private String description;
    private Integer courseId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer passingMarks;
    private Integer durationMinute;
    private Integer totalMarks;
    private String instruction;
    private List<QuestionDTO> questions=new ArrayList<>();
    
	public Integer getPassingMarks() {
		return passingMarks;
	}
	public void setPassingMarks(Integer passingMarks) {
		this.passingMarks = passingMarks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public Integer getDurationMinute() {
		return durationMinute;
	}
	public void setDurationMinute(Integer durationMinute) {
		this.durationMinute = durationMinute;
	}
	public Integer getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public List<QuestionDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}
	public TestFormDTO(String name, String description, Integer courseId, LocalDateTime startTime, LocalDateTime endTime,
			Integer durationMinute, Integer totalMarks, String instruction, List<QuestionDTO> questions,Integer passingMarks ) {
		super();
		this.name = name;
		this.description = description;
		this.courseId = courseId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.durationMinute = durationMinute;
		this.totalMarks = totalMarks;
		this.instruction = instruction;
		this.questions = questions;
		this.passingMarks=passingMarks;
	}
	public TestFormDTO() {
		super();
	}
	@Override
	public String toString() {
		return "TestFormDTO [name=" + name + ", description=" + description + ", courseId=" + courseId + ", startTime="
				+ startTime + ", endTime=" + endTime + ", durationMinute=" + durationMinute + ", totalMarks="
				+ totalMarks + ", instruction=" + instruction + ", questions=" + questions + "]";
	}
    
}
