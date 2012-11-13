package com.example.inspirationdraft;

import android.util.SparseArray;

public class LessonList {

	private SparseArray<LessonData> lessonMap = new SparseArray<LessonData>();
	
	public LessonData getLesson(int lessonID) {
		return lessonMap.get(lessonID);
	}

	public void putLesson(LessonData newLesson) {
		lessonMap.put(newLesson.getID(), newLesson);
	}
	
	public void removeLesson(int lessonID) {
		lessonMap.remove(lessonID);
	}
}
