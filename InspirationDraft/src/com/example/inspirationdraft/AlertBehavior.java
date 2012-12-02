package com.example.inspirationdraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;

public class AlertBehavior implements Iterable<String> {

	private Map<String,AlertData> alerts;
	
	public AlertBehavior() {
		alerts = new TreeMap<String, AlertData>();
	}
		
	public void addAlert(String id) {
		alerts.put(id, new AlertData());
	}

	public void addInspiration(String id, AlertData data) {
		alerts.put(id, data);
	}

	public void clear() {
		alerts.clear();
	}

	public boolean containsID(String id) {
		return alerts.keySet().contains(id);
	}

	public void removeID(String id) {
		alerts.remove(id);
	}

	public AlertData getAlert(String id) {
		return alerts.get(id);
	}

	@Override
	public Iterator<String> iterator() {
		return alerts.keySet().iterator();
	}

	public void save(File f) {
		try {
			ObjectOutputStream output =
					new ObjectOutputStream(
							new FileOutputStream(f));

			output.writeObject(alerts);

			output.close();

			Log.i("Inspiration Viewer", "alerts saved to " + f);

		} catch (FileNotFoundException ex) {
			Log.i("Inspiration Viewer", "Error:  File Can Not Be Created: " + f);
			Log.i("Inspiration Viewer", "Attempt to save alerts aborted.");
		} catch (IOException ex) {
			Log.i("Inspiration Viewer", "Fatal error while saving alerts.");
			Log.i("Inspiration Viewer", "Attempt to save alerts aborted.");
		}
	}

	@SuppressWarnings("unchecked")
	public void load(File f) {
		try {
			ObjectInputStream input = 
					new ObjectInputStream(
							new FileInputStream(f));

			alerts = (TreeMap<String,AlertData>) input.readObject();

			input.close();

			Log.i("Inspiration Viewer", "alerts loaded from " + f);

		} catch (FileNotFoundException ex) {
			Log.i("Inspiration Viewer", "File Not Found: " + f);
			Log.i("Inspiration Viewer", "Creating an empty set of alerts.");
			alerts.clear();
		} catch (IOException ex) {
			Log.i("Inspiration Viewer", "Fatal error while loading alerts.");
			Log.i("Inspiration Viewer", "Creating an empty set of alerts.");
			alerts.clear();
		} catch (ClassNotFoundException ex) {
			Log.i("Inspiration Viewer", "Fatal error while loading alerts.");
			Log.i("Inspiration Viewer", "Creating an empty set of alerts.");
			alerts.clear();
		}
		
	}

}
