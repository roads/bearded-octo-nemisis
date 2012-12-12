package com.example.inspirationdraft;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class AlertBehavior implements Serializable, Iterable<AlertData>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<AlertData> alerts;
	private static final String TAG = "AlertBehavior";
	
	public AlertBehavior() {
		alerts = new ArrayList<AlertData>(); 
	}
	
	public AlertBehavior(ArrayList<AlertData> alerts){
		alerts = new ArrayList<AlertData>(alerts);
	}
	
	public ArrayList<AlertData> getAlertArrayList(){
		return alerts;
	}
	
	public boolean isEmpty(){
		Log.v(TAG, "isEmpty"+alerts.isEmpty());
		Log.v(TAG, "alerts equals"+alerts);
		
		if (alerts == null | alerts.isEmpty()){
			return false;
		}
		return true;
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
		Log.v(TAG, "in getNumberOfAlerts");
		int temp = alerts.size();
		Log.v(TAG, "in getNumberOfAlerts 2");
		return temp;
	}

	@Override
	public Iterator<AlertData> iterator() {
		Iterator<AlertData> ialert = alerts.iterator(); 
		return ialert;
	}
	
}