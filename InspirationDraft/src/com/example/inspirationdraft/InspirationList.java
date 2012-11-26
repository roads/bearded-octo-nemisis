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

public class InspirationList implements Iterable<String> {

	private Map<String,InspirationData> inspirations;
	
	public InspirationList() {
		inspirations = new TreeMap<String,InspirationData>();
	}
	
	public void addInspiration(String id) {
		inspirations.put(id, new InspirationData());
	}
	
	public void addInspiration(String id, InspirationData data) {
		inspirations.put(id, data);
	}
	
	public void clear() {
		inspirations.clear();
	}
	
	public boolean containsID(String id) {
		return inspirations.keySet().contains(id);
	}
	
	public void removeID(String id) {
		inspirations.remove(id);
	}
	
	public InspirationData getID(String id) {
		return inspirations.get(id);
	}
	
	@Override
	public Iterator<String> iterator() {
		return inspirations.keySet().iterator();
	}

	public void save(File f) {
		try {
			ObjectOutputStream output =
					new ObjectOutputStream(
							new FileOutputStream(f));
			
			output.writeObject(inspirations);
			
			output.close();
			
			Log.i("Inspiration Viewer", "Inspirations saved to " + f);
			
		} catch (FileNotFoundException ex) {
			Log.i("Inspiration Viewer", "Error:  File Can Not Be Created: " + f);
			Log.i("Inspiration Viewer", "Attempt to save inspirations aborted.");
		} catch (IOException ex) {
			Log.i("Inspiration Viewer", "Fatal error while saving inspirations.");
			Log.i("Inspiration Viewer", "Attempt to save inspirations aborted.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(File f) {
		try {
			ObjectInputStream input = 
					new ObjectInputStream(
							new FileInputStream(f));
			
			inspirations = (TreeMap<String,InspirationData>) input.readObject();
			
			input.close();
			
			Log.i("Inspiration Viewer", "Inspirations loaded from " + f);
		
		} catch (FileNotFoundException ex) {
			Log.i("Inspiration Viewer", "File Not Found: " + f);
			Log.i("Inspiration Viewer", "Creating an empty set of inspirations.");
			inspirations.clear();
		} catch (IOException ex) {
			Log.i("Inspiration Viewer", "Fatal error while loading inspirations.");
			Log.i("Inspiration Viewer", "Creating an empty set of inspirations.");
			inspirations.clear();
		} catch (ClassNotFoundException ex) {
			Log.i("Inspiration Viewer", "Fatal error while loading inspirations.");
			Log.i("Inspiration Viewer", "Creating an empty set of inspirations.");
			inspirations.clear();
		}
	}
	
}