package com.example.inspirationdraft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class LessonData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final String EMPTY = "Empty";
	static int lessonCount = 1;
	private String lessonId;
	private String LessonTitle;
	private ArrayList<String> inspirationAssignments;
	private int assignedIcon;
	
	public LessonData() {
		this.lessonId = Integer.toString(lessonCount);
		lessonCount++;
		this.LessonTitle = ""; //"Lesson " + this.lessonId;
		this.inspirationAssignments = new ArrayList<String>();
		this.inspirationAssignments.add(EMPTY);
		this.assignedIcon = R.drawable.question_mark;
	}
	
	public LessonData(String suffixName){
		this.lessonId = Integer.toString(lessonCount);
		lessonCount++;
		this.LessonTitle = suffixName;//"Lesson " + this.lessonId + ":  " + suffixName;
		this.inspirationAssignments = new ArrayList<String>();
		this.inspirationAssignments.add(EMPTY);
		this.assignedIcon = R.drawable.question_mark;
	}

	public String getLessonId() {
		return this.lessonId;
	}

	public void setLessonId(String oldLessonId) {
		this.lessonId = oldLessonId;
	}
	public String getLessonTitle() {
		return this.LessonTitle;
	}
	
	public void setLessonTitle(String LessonTitle) {
		this.LessonTitle = LessonTitle;
	}
	
	public String getLessonAssignmentsString() {
		return this.inspirationAssignments.toString();
	}
	
	public int getAssignedIcon() {
		return this.assignedIcon;
	}
	
	public void addInspiration(String inspirationId) {
		inspirationAssignments.remove(EMPTY);
		inspirationAssignments.add(lessonId);
	}
	
	public void removeLesson(String lessonId) {
		inspirationAssignments.remove(lessonId);
		if (inspirationAssignments.size() == 0) {
			inspirationAssignments.add(EMPTY);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.lessonId = (String) stream.readObject();
		this.LessonTitle = (String) stream.readObject();
		this.inspirationAssignments = (ArrayList<String>) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.lessonId);
		stream.writeObject(this.LessonTitle);
		stream.writeObject(this.inspirationAssignments);
	}
}
