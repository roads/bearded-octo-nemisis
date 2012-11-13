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

public class InspirationList implements Iterable<Integer> {

	private Map<Integer,Inspiration> inspirations;
	
	public InspirationList() {
		inspirations = new TreeMap<Integer,Inspiration>();
	}
	
	public void addInspiration(Integer id) {
		inspirations.put(id, new Inspiration());
	}
	
	public void addInspiration(Integer id, Inspiration data) {
		inspirations.put(id, data);
	}
	
	public void clear() {
		inspirations.clear();
	}
	
	public boolean containsID(Integer id) {
		return inspirations.keySet().contains(id);
	}
	
	public void removeID(Integer id) {
		inspirations.remove(id);
	}
	
	public Inspiration getID(Integer id) {
		return inspirations.get(id);
	}
	
	public Iterator<Integer> iterator() {
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
			Log.i("Inspiration Viewer", "Attempt to save profiles aborted.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(File f) {
		try {
			ObjectInputStream input = 
					new ObjectInputStream(
							new FileInputStream(f));
			
			inspirations = (TreeMap<Integer,Inspiration>) input.readObject();
			
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