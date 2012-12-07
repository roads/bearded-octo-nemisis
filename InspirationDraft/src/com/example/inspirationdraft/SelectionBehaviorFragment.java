package com.example.inspirationdraft;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SelectionBehaviorFragment extends Fragment {

	private ArrayList<SelectionBehaviorData> selectionBehaviorsForDisplay = new ArrayList<SelectionBehaviorData>();
	private String lessonIdKey;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		lessonIdKey = getActivity().getIntent().getStringExtra("lessonIdKey");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		
		if (lessonIdKey != null) {
			
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_selection_behavior,container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_cancel_save, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == R.id.menu_ok) {
			saveData = true;
		} else if (item.getItemId() == R.id.menu_cancel) {
			saveData = false;
		} else if (item.getItemId() == android.R.id.home){
			saveData = false;
		}
		getActivity().finish();
		return true;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		selectionBehaviorsForDisplay.add(new SelectionBehaviorData("test1"));
		selectionBehaviorsForDisplay.add(new SelectionBehaviorData("test2"));
		
		ListView list_checked_inspirations = (ListView) getActivity().findViewById(R.id.inspirationlist);			
		
        SelectionBehaviorArrayAdapter adapter = new SelectionBehaviorArrayAdapter(getActivity(),
    			R.layout.listview_selection_row, selectionBehaviorsForDisplay);
		list_checked_inspirations.setAdapter(adapter);
		list_checked_inspirations.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
	}
	
}
