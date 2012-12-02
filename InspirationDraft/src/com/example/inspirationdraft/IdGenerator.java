package com.example.inspirationdraft;

public class IdGenerator {
	private int inspirationCount;
	private int lessonCount;

	public int getInspirationID() {
		int returnedCount = inspirationCount;
		inspirationCount++;
		return returnedCount;
	}
	
	public int getLessonID() {
		int returnedCount = lessonCount;
		lessonCount++;
		return returnedCount;
	
	}
	//test

}
