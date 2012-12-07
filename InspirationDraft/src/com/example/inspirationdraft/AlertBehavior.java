package com.example.inspirationdraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;

public class AlertBehavior {

	private ArrayList<AlertData> alerts;
	
	public AlertBehavior() {
		alerts = new ArrayList<AlertData>();
	}
		
	public void addAlert(AlertData data) {
		alerts.add(data);
	}

	public void clear() {
		alerts.clear();
	}
	
	public void removeByIndex(int index) {
		alerts.remove(index);
	}
	
	public void removeByObject(AlertData data){
		alerts.remove(data);
	}

	public AlertData getAlertByIndex(int index) {
		return alerts.get(index);
	}
}