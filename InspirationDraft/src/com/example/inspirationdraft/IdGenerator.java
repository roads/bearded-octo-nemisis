package com.example.inspirationdraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Log;

public class IdGenerator implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int inspirationCount;
	private int lessonCount;

	public String getInspirationId() {
		int returnedCount = inspirationCount;
		inspirationCount++;
		return Integer.toString(returnedCount);
	}
	
	public String getLessonId() {
		int returnedCount = lessonCount;
		lessonCount++;
		return Integer.toString(returnedCount);
	
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.inspirationCount = (Integer) stream.readObject();
		this.lessonCount = (Integer) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.inspirationCount);
		stream.writeObject(this.lessonCount);
	}
	
//	public void save(File f) {
//		try {
//			ObjectOutputStream output =
//					new ObjectOutputStream(
//							new FileOutputStream(f));
//			
//			output.writeObject(this);
//			
//			output.close();
//			
//			Log.i("IdGenerator", "Ids saved to " + f);
//			
//		} catch (FileNotFoundException ex) {
//			Log.i("IdGenerator", "Error:  File Can Not Be Created: " + f);
//			Log.i("IdGenerator", "Attempt to save lessons aborted.");
//		} catch (IOException ex) {
//			Log.i("IdGenerator", "Fatal error while saving lessons.");
//			Log.i("IdGenerator", "Attempt to save lessons aborted.");
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public void load(File f) {
//		try {
//			ObjectInputStream input = 
//					new ObjectInputStream(
//							new FileInputStream(f));
//			
//			ids = (IdGenerator) input.readObject();
//			
//			input.close();
//			
//			Log.i("IdGenerator", "Lessons loaded from " + f);
//		
//		} catch (FileNotFoundException ex) {
//			Log.i("IdGenerator", "File Not Found: " + f);
//			Log.i("IdGenerator", "Creating an empty set of lessons.");
//			ids.clear();
//		} catch (IOException ex) {
//			Log.i("IdGenerator", "Fatal error while loading lessons.");
//			Log.i("IdGenerator", "Creating an empty set of lessons.");
//			ids.clear();
//		} catch (ClassNotFoundException ex) {
//			Log.i("IdGenerator", "Fatal error while loading lessons.");
//			Log.i("IdGenerator", "Creating an empty set of lessons.");
//			ids.clear();
//		}
//	}
}
