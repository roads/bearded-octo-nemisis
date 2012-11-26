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
	private String lessonID;
	private String lessonName;
	private ArrayList<String> inspirationAssignments;
	
	public LessonData() {
		this.lessonID = Integer.toString(lessonCount);
		lessonCount++;
		this.lessonName = ""; //"Lesson " + this.lessonID;
		this.inspirationAssignments = new ArrayList<String>();
		this.inspirationAssignments.add(EMPTY);
	}
	
	public LessonData(String suffixName){
		this.lessonID = Integer.toString(lessonCount);
		lessonCount++;
		this.lessonName = suffixName;//"Lesson " + this.lessonID + ":  " + suffixName;
		this.inspirationAssignments = new ArrayList<String>();
		this.inspirationAssignments.add(EMPTY);
	}

	public String getLessonID() {
		return this.lessonID;
	}
	
	public String getLessonName() {
		return this.lessonName;
	}
	
	public ArrayList<String> getLessonInspirations() {
		return this.inspirationAssignments;
	}
	
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	
	public void addInspiration(String inspirationID) {
		inspirationAssignments.remove(EMPTY);
		inspirationAssignments.add(lessonID);
	}
	
	public void removeLesson(String lessonID) {
		inspirationAssignments.remove(lessonID);
		if (inspirationAssignments.size() == 0) {
			inspirationAssignments.add(EMPTY);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.lessonID = (String) stream.readObject();
		this.lessonName = (String) stream.readObject();
		this.inspirationAssignments = (ArrayList<String>) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.lessonID);
		stream.writeObject(this.lessonName);
		stream.writeObject(this.inspirationAssignments);
	}
}
