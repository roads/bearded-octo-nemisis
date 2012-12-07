package com.example.inspirationdraft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class InspirationData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final String UNASSIGNED = "Unassigned";
	static int inspirationCount = 1;
	private String inspirationId;
	private String content;
	private ArrayList<String> lessonAssignments;
	
	public InspirationData(String inspirationId, String content){
		super();
		this.inspirationId = inspirationId;
		this.content = content;
		this.lessonAssignments = new ArrayList<String>();
		this.lessonAssignments.add(UNASSIGNED);
	}
	
	public InspirationData(String inspirationId, String content, ArrayList<String> assignments){
		super();
		this.inspirationId = inspirationId;
		this.content = content;
		this.lessonAssignments = assignments;
	}

	public String getInspirationId() {
		return this.inspirationId;
	}

	public void setInspirationId(String newId) {
		this.inspirationId = newId;
	}
	
	public String getInspirationContent() {
		return this.content;
	}
	
	public void setInspirationContent(String content) {
		this.content = content;
	}
	
	public ArrayList<String> getLessonAssignments() {
		return this.lessonAssignments;
	}
	
	public void setLessonAssignments(ArrayList<String> newAssignments) {
		this.lessonAssignments = newAssignments;
	}
	
	public int getAssignedIcon() {
		lessonAssignments.remove(UNASSIGNED);
		if (lessonAssignments.size() > 0) {
			return R.drawable.flag_green;
		} else {
			lessonAssignments.add(UNASSIGNED);
			return R.drawable.flag_red;
		}
	}
	
	public void addLessonAssignment(String lessonId) {
		lessonAssignments.remove(UNASSIGNED);
		lessonAssignments.add(lessonId);
	}
	
	public void removeLessonAssignment(String lessonId) {
		lessonAssignments.remove(lessonId);
		if (lessonAssignments.size() == 0) {
			lessonAssignments.add(UNASSIGNED);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.inspirationId = (String) stream.readObject();
		this.content = (String) stream.readObject();
		this.lessonAssignments = (ArrayList<String>) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.inspirationId);
		stream.writeObject(this.content);
		stream.writeObject(this.lessonAssignments);
	}
}
