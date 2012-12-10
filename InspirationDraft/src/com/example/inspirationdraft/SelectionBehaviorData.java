package com.example.inspirationdraft;

public class SelectionBehaviorData {

	private String selectionBehaviorName;
	private int listPostion;
	
	public SelectionBehaviorData(Integer position, String name) {
		this.listPostion = position;
		this.selectionBehaviorName = name;
	}
	
	public String getSelectionBehaviorName() {
		return selectionBehaviorName;
	}
	
	public Integer getListPosition() {
		return this.listPostion;
	}

}
