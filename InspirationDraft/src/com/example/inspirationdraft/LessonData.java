package com.example.inspirationdraft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class LessonData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final String EMPTY = "Empty";
	private String lessonId;
	private String lessonTitle;
	private ArrayList<String> inspirationAssignments;
	private Integer selectionBehavior;
	private ArrayList<Integer> alertBehavior;
	private int inspirationCounter;
	private Random randGenerator = new Random();
	
	public LessonData(String lessonId, String lessonTitle){
		super();
		this.lessonId = lessonId;
		this.lessonTitle = lessonTitle;
		this.inspirationAssignments = new ArrayList<String>();
		this.inspirationAssignments.add(EMPTY);
		this.selectionBehavior = 0;
		this.alertBehavior = new ArrayList<Integer>();
	}
	
	public String getNextInspiration() {
		int numInspirations = inspirationAssignments.size();
		
		String nextInspirationId = null;
		if (numInspirations > 0) {
			if (selectionBehavior == 0) {
				 int randomInt = randGenerator.nextInt(numInspirations);
				 nextInspirationId = Integer.toString(randomInt);
			} else if (selectionBehavior == 1) {
				nextInspirationId = inspirationAssignments.get(inspirationCounter);
				inspirationCounter = (inspirationCounter + 1)%numInspirations;
			}
			
		}
		
		return nextInspirationId;
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
		inspirationCounter = 0;
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
	
	public Integer getSelectionBehavior() {
		return this.selectionBehavior;
	}
	
	public void setSelectionBehavior(Integer behavior) {
		this.selectionBehavior = behavior;
	}
	
	public ArrayList<Integer> getAlertBehavior() {
		return this.alertBehavior;
	}
	
	public void setAlertBehavior(ArrayList<Integer> behavior) {
		this.alertBehavior = behavior;
	}
	
	public void addInspirationAssignment(String inspirationId) {
		inspirationAssignments.remove(EMPTY);
		inspirationAssignments.add(inspirationId);
		inspirationCounter = 0;
	}
	
	public void removeInspirationAssignment(String inspiraitonId) {
		inspirationAssignments.remove(inspiraitonId);
		if (inspirationAssignments.size() == 0) {
			inspirationAssignments.add(EMPTY);
		}
		inspirationCounter = 0;
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.lessonId = (String) stream.readObject();
		this.lessonTitle = (String) stream.readObject();
		this.inspirationAssignments = (ArrayList<String>) stream.readObject();
		this.selectionBehavior = (Integer) stream.readObject();
		this.inspirationCounter = (Integer) stream.readObject();
		this.alertBehavior = (ArrayList<Integer>) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.lessonId);
		stream.writeObject(this.lessonTitle);
		stream.writeObject(this.inspirationAssignments);
		stream.writeObject(this.selectionBehavior);
		stream.writeObject(this.inspirationCounter);
		stream.writeObject(this.alertBehavior);
	}
}
