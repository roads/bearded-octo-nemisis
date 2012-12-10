package com.example.inspirationdraft;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;


public class EditAlertFragment extends Fragment{
	private String lessonIdKey;
	private int alertIndex;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int defaultValue = -1;
		setHasOptionsMenu(true);
		lessonIdKey = getActivity().getIntent().getStringExtra("lessonIdKey");
		alertIndex = getActivity().getIntent().getIntExtra("alertIndex", defaultValue);
//		inspirationsForStorage.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
	}
	

}
