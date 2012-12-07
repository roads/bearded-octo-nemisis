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

public class IdGeneratorList implements Iterable<String> {

	private Map<String,IdGenerator> idGenerators;
	
	public IdGeneratorList() {
		idGenerators = new TreeMap<String,IdGenerator>();
	}
	
	public void addIdGenerator(String generatorType, IdGenerator idGenerator) {
		idGenerators.put(generatorType, idGenerator);
	}
	
	public void clear() {
		idGenerators.clear();
	}
	
	public boolean containsIdGenerator(String generatorType) {
		return idGenerators.keySet().contains(generatorType);
	}
	
	public void removeIdGenerator(String generatorType) {
		idGenerators.remove(generatorType);
	}
	
	public IdGenerator getIdGenerator(String generatorType) {
		return idGenerators.get(generatorType);
	}
	
	@Override
	public Iterator<String> iterator() {
		return idGenerators.keySet().iterator();
	}

	public void save(File f) {
		try {
			ObjectOutputStream output =
					new ObjectOutputStream(
							new FileOutputStream(f));
			
			output.writeObject(idGenerators);
			
			output.close();
			Log.i("IdGenerator", "Ids saved to " + f);
			
		} catch (FileNotFoundException ex) {
			Log.i("IdGeneratorList", "Error:  File Can Not Be Created: " + f);
			Log.i("IdGeneratorList", "Attempt to save idGenerators aborted.");
		} catch (IOException ex) {
			Log.i("IdGeneratorList", "Fatal error while saving idGenerators.");
			Log.i("IdGeneratorList", "Attempt to save idGenerators aborted.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(File f) {
		try {
			ObjectInputStream input = 
					new ObjectInputStream(
							new FileInputStream(f));
			
			idGenerators = (TreeMap<String,IdGenerator>) input.readObject();
			
			input.close();
			
			Log.i("Lesson Viewer", "Lessons loaded from " + f);
		
		} catch (FileNotFoundException ex) {
			Log.i("IdGeneratorList", "File Not Found: " + f);
			Log.i("IdGeneratorList", "Creating an empty set of lessons.");
			idGenerators.clear();
		} catch (IOException ex) {
			Log.i("IdGeneratorList", "Fatal error while loading lessons.");
			Log.i("IdGeneratorList", "Creating an empty set of lessons.");
			idGenerators.clear();
		} catch (ClassNotFoundException ex) {
			Log.i("IdGeneratorList", "Fatal error while loading lessons.");
			Log.i("IdGeneratorList", "Creating an empty set of lessons.");
			idGenerators.clear();
		}
	}
	
}