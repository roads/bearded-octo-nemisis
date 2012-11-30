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
	private String inspirationID;
	private String content;
	private ArrayList<String> lessonAssignments;
	private int assignedIcon;
	
	public InspirationData() {
		this.inspirationID = Integer.toString(inspirationCount);
		inspirationCount++;
		this.content = "A man who does not read good books has no advantage over the man who can't read them. -Mark Twain";
		this.lessonAssignments = new ArrayList<String>();
		this.lessonAssignments.add(UNASSIGNED);
		this.assignedIcon = R.drawable.question_mark;
	}
	
	public InspirationData(String content){
		this.inspirationID = Integer.toString(inspirationCount);
		inspirationCount++;
		this.content = content;
		this.lessonAssignments = new ArrayList<String>();
		this.lessonAssignments.add(UNASSIGNED);
		this.assignedIcon = R.drawable.question_mark;
	}

	public String getID() {
		return this.inspirationID;
	}

	public void setID(String newID) {
		this.inspirationID = newID;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public ArrayList<String> getLessonAssignments() {
		return this.lessonAssignments;
	}
	
	public void setLessonAssignments(ArrayList<String> newAssignments) {
		this.lessonAssignments = newAssignments;
	}
	
	public int getAssignedIcon() {
		return this.assignedIcon;
	}
	
	public void addLesson(String lessonID) {
		lessonAssignments.remove(UNASSIGNED);
		lessonAssignments.add(lessonID);
	}
	
	public void removeLesson(String lessonID) {
		lessonAssignments.remove(lessonID);
		if (lessonAssignments.size() == 0) {
			lessonAssignments.add(UNASSIGNED);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.inspirationID = (String) stream.readObject();
		this.content = (String) stream.readObject();
		this.lessonAssignments = (ArrayList<String>) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.inspirationID);
		stream.writeObject(this.content);
		stream.writeObject(this.lessonAssignments);
	}
}
