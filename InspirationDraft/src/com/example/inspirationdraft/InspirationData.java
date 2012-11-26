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
	
	public InspirationData() {
		this.inspirationID = Integer.toString(inspirationCount);
		inspirationCount++;
		this.content = "A man who does not read good books has no advantage over the man who can't read them. -Mark Twain";
		this.lessonAssignments = new ArrayList<String>();
		this.lessonAssignments.add(UNASSIGNED);
	}
	
	public InspirationData(String content){
		this.inspirationID = Integer.toString(inspirationCount);
		inspirationCount++;
		this.content = content;
		this.lessonAssignments = new ArrayList<String>();
		this.lessonAssignments.add(UNASSIGNED);
	}

	public String getID() {
		return this.inspirationID;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public ArrayList<String> getLessonAssignments() {
		return this.lessonAssignments;
	}
	
	public void setContent(String content) {
		this.content = content;
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
	

	public String toListFormatting() {
		String text = this.inspirationID + "\n" + this.content + "\n" + this.lessonAssignments;
		return text;
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
