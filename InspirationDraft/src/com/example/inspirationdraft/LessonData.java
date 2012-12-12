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
	
	public LessonData(String lessonId, String lessonTitle) {
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
//				nextInspirationId = "4";
				Random randGenerator = new Random();
				 int randomInt = randGenerator.nextInt(numInspirations);
				 nextInspirationId = inspirationAssignments.get(randomInt);
			} else if (selectionBehavior == 1) {
				//nextInspirationId = "3";
				if(this.inspirationCounter >= (numInspirations)) {
					this.inspirationCounter = 0;
				}
				nextInspirationId = inspirationAssignments.get(this.inspirationCounter);
				this.inspirationCounter = this.inspirationCounter + 1;
			}			
		} else {
			nextInspirationId = EMPTY;
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
