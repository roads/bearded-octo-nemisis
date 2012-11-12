package com.example.inspirationdraft;

import android.util.SparseArray;

public class LessonList {

	private SparseArray<Lesson> lessonMap = new SparseArray<Lesson>();
	
	public Lesson getLesson(int lessonID) {
		return lessonMap.get(lessonID);
	}

	public void putLesson(Lesson newLesson) {
		lessonMap.put(newLesson.getID(), newLesson);
	}
	
	public void removeLesson(int lessonID) {
		lessonMap.remove(lessonID);
	}
}
