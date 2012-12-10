package com.example.inspirationdraft;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class AlertBehavior implements Serializable, Iterable<AlertData>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public int getNumberOfAlerts(){
		return alerts.size();
	}

	@Override
	public Iterator<AlertData> iterator() {
		Iterator<AlertData> ialert = alerts.iterator(); 
		return ialert;
	}
	
}