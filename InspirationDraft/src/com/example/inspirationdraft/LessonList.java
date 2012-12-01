package com.example.inspirationdraft;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;

public class LessonList implements Iterable<String> {

	private Map<String,LessonData> lessons;
	
	public LessonList() {
		lessons = new TreeMap<String,LessonData>();
	}
	
	public void addLesson(String lessonName) {
		lessons.put(lessonName, new LessonData());
	}
	
	public void addLesson(String lessonName, LessonData data) {
		lessons.put(lessonName, data);
	}
	
	public void clear() {
		lessons.clear();
	}
	
	public boolean containsLesson(String lessonName) {
		return lessons.keySet().contains(lessonName);
	}
	
	public void removeLesson(String lessonName) {
		lessons.remove(lessonName);
	}
	
	public LessonData getLesson(String lessonName) {
		return lessons.get(lessonName);
	}
	
	@Override
	public Iterator<String> iterator() {
		return lessons.keySet().iterator();
	}

	public void save(File f) {
		try {
			ObjectOutputStream output =
					new ObjectOutputStream(
							new FileOutputStream(f));
			
			output.writeObject(lessons);
			
			output.close();
			
			Log.i("Lesson Viewer", "Lessons saved to " + f);
			
		} catch (FileNotFoundException ex) {
			Log.i("Lesson Viewer", "Error:  File Can Not Be Created: " + f);
			Log.i("Lesson Viewer", "Attempt to save lessons aborted.");
		} catch (IOException ex) {
			Log.i("Lesson Viewer", "Fatal error while saving lessons.");
			Log.i("Lesson Viewer", "Attempt to save lessons aborted.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(File f) {
		try {
			ObjectInputStream input = 
					new ObjectInputStream(
							new FileInputStream(f));
			
			lessons = (TreeMap<String,LessonData>) input.readObject();
			
			input.close();
			
			Log.i("Lesson Viewer", "Lessons loaded from " + f);
		
		} catch (FileNotFoundException ex) {
			Log.i("Lesson Viewer", "File Not Found: " + f);
			Log.i("Lesson Viewer", "Creating an empty set of lessons.");
			lessons.clear();
		} catch (IOException ex) {
			Log.i("Lesson Viewer", "Fatal error while loading lessons.");
			Log.i("Lesson Viewer", "Creating an empty set of lessons.");
			lessons.clear();
		} catch (ClassNotFoundException ex) {
			Log.i("Lesson Viewer", "Fatal error while loading lessons.");
			Log.i("Lesson Viewer", "Creating an empty set of lessons.");
			lessons.clear();
		}
	}
	
}