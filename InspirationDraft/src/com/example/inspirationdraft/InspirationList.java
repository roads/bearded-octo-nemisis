package com.example.inspirationdraft;

import android.util.SparseArray;

public class InspirationList {

	private SparseArray<Inspiration> inspirationMap = new SparseArray<Inspiration>();
	
	public Inspiration getInspiration(int inspirationID) {
		return inspirationMap.get(inspirationID);
	}

	public void putInspiration(Inspiration newInspiration) {
		inspirationMap.put(newInspiration.getID(), newInspiration);
	}
	
	public void removeInspiration(int inspirationID) {
		inspirationMap.remove(inspirationID);
	}
	
}
