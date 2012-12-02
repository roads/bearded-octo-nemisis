package com.example.inspirationdraft;

import java.io.Serializable;

import android.text.format.Time;

public class AlertData implements Serializable{
	private Boolean isRepeating;
	private Time time; 
	private Boolean exists;
	
	public AlertData() {
		// TODO Auto-generated constructor stub
		setIsRepeating(false);
		setExists(false);
	}
	public AlertData(Time time){
		setTime(time);
		setIsRepeating(false);
		setExists(true);
	}
	public AlertData(Time time, Boolean isRepeating){
		setTime(time);
		setIsRepeating(isRepeating);
		setExists(true);
	}
	
	public Boolean getIsRepeating() {
		return isRepeating;
	}

	public void setIsRepeating(Boolean isRepeating) {
		this.isRepeating = isRepeating;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Boolean getExists() {
		return exists;
	}

	public void setExists(Boolean exists) {
		this.exists = exists;
	}
	
	

}
