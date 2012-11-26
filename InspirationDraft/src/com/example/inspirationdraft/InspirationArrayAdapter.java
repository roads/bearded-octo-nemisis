package com.example.inspirationdraft;

import android.content.Context;

public class InspirationArrayAdapter extends TwoLineArrayAdapter<InspirationData> {
	public InspirationArrayAdapter(Context context, InspirationData[] inspirations) {
		super(context,inspirations);
	}

	@Override
	public String lineOneText(InspirationData i) {
		return i.getID();
	}
	
	@Override
	public String lineTwoText(InspirationData i) {
		return i.getContent();
	}
}
