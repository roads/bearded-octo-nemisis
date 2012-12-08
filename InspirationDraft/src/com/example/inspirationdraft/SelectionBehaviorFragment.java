package com.example.inspirationdraft;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectionBehaviorFragment extends Fragment {

	private LessonList lessonsForStorage = new LessonList();
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
		
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
	
		if (lessonIdKey != null) {
			LessonData lessonData = lessonsForStorage.getLesson(lessonIdKey);
			
			TextView lesson_id = (TextView) getActivity().findViewById(R.id.lesson_id);			
			lesson_id.setText(lessonIdKey);
			
			TextView lesson_title = (TextView) getActivity().findViewById(R.id.lesson_title);			
			lesson_title.setText(lessonData.getLessonTitle());
			
			ListView list_checkable_selections = (ListView) getActivity().findViewById(R.id.selectionlist);
			Integer selectionBehaviorPosition = lessonData.getSelectionBehavior();
			list_checkable_selections.setItemChecked(selectionBehaviorPosition, true);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
		
			LessonData lessonData = null;
			
			int newSelectionBehavior = getNewChosenSelectionBehavior();
			
			if (lessonIdKey != null) {
				// editing existing
				lessonData = lessonsForStorage.getLesson(lessonIdKey);
				lessonData.setSelectionBehavior(newSelectionBehavior);
				lessonsForStorage.removeLesson(lessonIdKey);
			} 
			
			lessonsForStorage.addLesson(lessonIdKey, lessonData);
			lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));
			
			CharSequence text = getText(R.string.toast_lesson_saved);
			Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
			toast.show();
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
		
		selectionBehaviorsForDisplay.add(new SelectionBehaviorData(0,"Random"));
		selectionBehaviorsForDisplay.add(new SelectionBehaviorData(1,"Cycle"));
		
		ListView list_checked_inspirations = (ListView) getActivity().findViewById(R.id.selectionlist);			
		
        SelectionBehaviorArrayAdapter adapter = new SelectionBehaviorArrayAdapter(getActivity(),
    			R.layout.listview_selection_row, selectionBehaviorsForDisplay);
		list_checked_inspirations.setAdapter(adapter);
		list_checked_inspirations.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
	}
	
private int getNewChosenSelectionBehavior(){
		
		// Determine which selectionBehavior was selected
		ListView list_checkable_selections = (ListView) getActivity().findViewById(R.id.selectionlist);
		int numSelectionBehaviorsInView = list_checkable_selections.getCount();
		SparseBooleanArray chosenSelectionBehaviorSparseBooleanArray = list_checkable_selections.
				getCheckedItemPositions();
		
		int newChosenSelectionBehavior = 0;
		
		for(int i =0; i < numSelectionBehaviorsInView; i++) {
			if(chosenSelectionBehaviorSparseBooleanArray.get(i) == true) {
				newChosenSelectionBehavior = i;
			}	
		}
		return newChosenSelectionBehavior;
	} 
}
