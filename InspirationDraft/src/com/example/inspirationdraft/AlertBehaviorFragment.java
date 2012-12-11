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

public class AlertBehaviorFragment extends Fragment {

	private LessonList lessonsForStorage = new LessonList();
	private ArrayList<AlertData> myAlerts = new ArrayList<AlertData>(); 
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
	
			//ListView pre-check stuff
			ListView list_checkable_alerts = (ListView) getActivity().findViewById(R.id.alertlist);
			ArrayList<Integer> alertAssignments = lessonData.getAlertBehavior();
			if (alertAssignments.size() > 0) {
				for (Integer alertId : alertAssignments) {
					list_checkable_alerts.setItemChecked(alertId, true);
				}
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
			LessonData lessonData = null;
			
			ArrayList<Integer> newSelectedAlertBehavior = getNewSelectedAlertBehavior();
			
			if (lessonIdKey != null) {
				// editing existing
				lessonData = lessonsForStorage.getLesson(lessonIdKey);
				lessonData.setAlertBehavior(newSelectedAlertBehavior);
				setAlarms(newSelectedAlertBehavior);
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
		return inflater.inflate(R.layout.fragment_alert_behavior,container, false);
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_alert_behavior, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == R.id.menu_ok) {
			saveData = true;
			getActivity().finish();
		}
		if (item.getItemId() == R.id.menu_cancel) {
			saveData = false;
			getActivity().finish();
		}
		
		if (item.getItemId() == android.R.id.home){
			saveData = false;
			getActivity().finish();
		}
//		Intent intent = new Intent(getActivity(), EditLessonActivity.class);
//		if (item.getItemId() == R.id.menu_new_alert){
//			saveData = false;
//			getActivity().finish();
//		}
//		if (item.getItemId() == R.id.menu_delete_alert){
//			saveData = false;
//			getActivity().finish();
//		}
//		if (item.getItemId() == R.id.menu_edit_alert){
//			saveData = false;
//			getActivity().finish();
//		}
		
		return true;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		myAlerts.add(new AlertData("in 30 seconds", ""));
		myAlerts.add(new AlertData("02", "10"));
		myAlerts.add(new AlertData("02", "14"));
		
		ListView list_checked_alerts = (ListView) getActivity().findViewById(R.id.alertlist);
		
		AlertArrayAdapter adapter = new AlertArrayAdapter(getActivity(),
    			R.layout.listview_alert_row, myAlerts);
		list_checked_alerts.setAdapter(adapter);
		list_checked_alerts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	private ArrayList<Integer> getNewSelectedAlertBehavior(){
		
		// Determine which alerts were selected
		ListView list_checkable_alerts = (ListView) getActivity().findViewById(R.id.alertlist);
		int numAlertsInView = list_checkable_alerts.getCount();
		ArrayList<Integer> newSelectedAlerts = new ArrayList<Integer>();
		SparseBooleanArray chosenInspirationsSparseBooleanArray = list_checkable_alerts.
				getCheckedItemPositions();
		
		for(int i =0; i < numAlertsInView; i++) {
			if(chosenInspirationsSparseBooleanArray.get(i) == true) {
				newSelectedAlerts.add(i);
			}	
		}
		return newSelectedAlerts;
	} 
	
	private void setAlarms(ArrayList<Integer> selectedAlertBehavior) {
	
		if (selectedAlertBehavior.size() > 0) {
			
			
		}
		
	}
	
}