package com.example.inspirationdraft;

import java.util.ArrayList;

public class Lesson {

	static int lessonCount;
	private int lessonID;
	private int lessonLength;
	private int lessonCounter;
	private ArrayList<Integer> idList;
	//private SelectionBehavior selectionBehavior;
	//private AlertBehavior alertBehavior;
	
	public Lesson() {
		this.lessonID = lessonCount;
		lessonCount++;
	}
	
	public void setSelectionBehavior(){
		//stub
	}
	
	public void setAlertBehavior(){
		//stub
	}
	
	public int getID() {
		return lessonID;
	}
	
	public void addInspirationID(Inspiration newInspiration){
		idList.add(newInspiration.getID());
		lessonLength++;
	}
	
	public int getNextInspirationID(){
		int nextID = idList.get(lessonCounter);
		lessonCounter = (lessonCounter + 1)%lessonLength;
		return nextID;
	}
	
	
	
	
}
