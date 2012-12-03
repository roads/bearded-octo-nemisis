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
	private String lessonTitle;
	private ArrayList<String> inspirationAssignments;
	
	public LessonData(String lessonId, String lessonTitle){
		super();
		this.lessonId = lessonId;
		this.lessonTitle = lessonTitle;
		this.inspirationAssignments = new ArrayList<String>();
		this.inspirationAssignments.add(EMPTY);
	}

	public LessonData(String lessonId, String suffixName, ArrayList<String> assignments){
		super();
		this.lessonId = lessonId;
		this.lessonTitle = suffixName;
		this.inspirationAssignments = assignments;
	}
	
	public String getLessonId() {
		return this.lessonId;
	}

	public void setLessonId(String oldLessonId) {
		this.lessonId = oldLessonId;
	}
	public String getLessonTitle() {
		return this.lessonTitle;
	}
	
	public void setLessonTitle(String LessonTitle) {
		this.lessonTitle = LessonTitle;
	}
	
	public ArrayList<String> getInspirationAssignments() {
		return this.inspirationAssignments;
	}
	
	public void setInspirationAssignments(ArrayList<String> newAssignments) {
		this.inspirationAssignments = newAssignments;
	}
	
	public int getAssignedIcon() {
		inspirationAssignments.remove(EMPTY);
		if (inspirationAssignments.size() > 0) {
			return R.drawable.flag_green;
		} else {
			inspirationAssignments.add(EMPTY);
			return R.drawable.flag_red;
		}
					
	}
	
	public void addInspirationAssignment(String inspirationId) {
		inspirationAssignments.remove(EMPTY);
		inspirationAssignments.add(inspirationId);
	}
	
	public void removeInspirationAssignment(String inspiraitonId) {
		inspirationAssignments.remove(inspiraitonId);
		if (inspirationAssignments.size() == 0) {
			inspirationAssignments.add(EMPTY);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.lessonId = (String) stream.readObject();
		this.lessonTitle = (String) stream.readObject();
		this.inspirationAssignments = (ArrayList<String>) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.lessonId);
		stream.writeObject(this.lessonTitle);
		stream.writeObject(this.inspirationAssignments);
	}
}
