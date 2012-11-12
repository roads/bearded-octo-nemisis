package com.example.inspirationdraft;

public class Inspiration {
	static int inspirationCount;
	private int inspirationID;
	private String content;
	
	public Inspiration(String content){
		this.inspirationID = inspirationCount;
		inspirationCount++;
		this.content = content;
	}

	public int getID() {
		return inspirationID;
	}
	
	public String getContent() {
		return content;
	}
}
