package com.example.inspirationdraft;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class IdGenerator implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idType;
	private int idCount;
	
	public IdGenerator(String idType){
		super();
		this.idType = idType;
		this.idCount = 1;
	}
	
	public String getUniqueId() {
		int idToReturn = idCount;
		this.idCount++;
		return Integer.toString(idToReturn);
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.idType = (String) stream.readObject();
		this.idCount = (Integer) stream.readObject();
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.idType);
		stream.writeObject(this.idCount);
	}
}
