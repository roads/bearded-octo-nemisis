package com.example.inspirationdraft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Inspiration implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	static int inspirationCount;
	private int inspirationID;
	private String content;
	
	public Inspiration() {
		this.inspirationID = inspirationCount;
		inspirationCount++;
		this.content = "A man who does not read good books has no advantage over the man who can't read them. -Mark Twain";
	}
	
	public Inspiration(String content){
		this.inspirationID = inspirationCount;
		inspirationCount++;
		this.content = content;
	}

	public int getID() {
		return this.inspirationID;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.inspirationID = (Integer) stream.readObject();
		this.content = (String) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.inspirationID);
		stream.writeObject(this.content);
	}
}
