package com.example.inspirationdraft;

import java.io.File;
import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlertBehaviorFragment extends ListFragment {
//	private AlertBehavior alertBehaviorForStorage = new AlertBehavior();
//	private ArrayList<AlertData> alertsForDisplay = new ArrayList<AlertData>();
//	private int itemSelected = -1;
	
	private IdGeneratorList idGeneratorsForStorage = new IdGeneratorList();
	private LessonList lessonsForStorage = new LessonList();
	private AlertBehavior myAlerts = new AlertBehavior();
//	private AlertBehavior alertsFromStorage = new AlertBehavior();
	private InspirationList inspirationsForStorage = new InspirationList();
	private ArrayList<LessonData> lessonsForDisplay = new ArrayList<LessonData>();
	private String lessonIdKey;
	private String inspirationIdKey;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstancesState) {
		super.onCreate(savedInstancesState);
		setHasOptionsMenu(true);
		
		lessonIdKey = getActivity().getIntent().getStringExtra("lessonIdKey");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		idGeneratorsForStorage.load(new File(getActivity().getFilesDir(), "idgenerators.bin"));
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
		
		if(lessonIdKey != null){
			LessonData newLessonData = lessonsForStorage.getLesson(lessonIdKey);
			
			ListView list_checkable_alerts = (ListView) getActivity().findViewById(R.id.alertlist);
			myAlerts = newLessonData.getAlerts();
			if(myAlerts.getNumberOfAlerts() > 0){
				int itemLocation = 0;
				for (AlertData a : myAlerts){
					if(a.getExists()){
						list_checkable_alerts.setItemChecked(itemLocation, true);
					}
					itemLocation++;
				}
			}
		}
		//setListAdapter(getCurrentAlerts());
	}

	@Override
	public void onPause() {
		super.onPause();
		if(saveData) {
			AlertData newAlertData = null;
			
			ArrayList<AlertData> newChosenAlertAssignments = 
					getNewChosenAlertAssignments();
			if (lessonIdKey != null){
				
			} else {
				
			}
			
		}
		//alerts.save(new File(getActivity().getFilesDir(), "lessons.bin"));
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_alert_behavior,container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// menu with new, delete, and edit options
		inflater.inflate(R.menu.fragment_alert_behavior,menu);
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
        
        for (Alert a: al)
        setEmptyText(getText(R.string.empty_lessonlist));
        setListAdapter(getCurrentLessons());
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);       
    }
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem delete = menu.findItem(R.id.menu_delete_lesson);
		MenuItem edit = menu.findItem(R.id.menu_edit_lesson);
		if (itemSelected != -1) {
			delete.setEnabled(true);
			edit.setEnabled(true);
		} else {
			delete.setEnabled(false);
			edit.setEnabled(false);
		}
	}
	
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelected = position;
		getActivity().invalidateOptionsMenu();
	}
	
	
    
    public void remove_id(String lessonIdKey) {
    	//remove lesson and save new lesson list
    	lessonsForStorage.removeLesson(lessonIdKey);
    	lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));
    	
    	//update inspirations to reflect removal and save
    	inspirationsForStorage.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
    	InspirationData newInspirationData = null;
    	for (String inspirationIdKey:inspirationsForStorage) {
			InspirationData oldInspirationData = inspirationsForStorage.getInspiration(inspirationIdKey);
			newInspirationData = new InspirationData(oldInspirationData.getInspirationId(), oldInspirationData.
					getInspirationContent(), oldInspirationData.getLessonAssignments());	
			newInspirationData.removeLessonAssignment(lessonIdKey);
			inspirationsForStorage.addInspiration(inspirationIdKey, newInspirationData);
		}
		inspirationsForStorage.save(new File(getActivity().getFilesDir(), "inspirations.bin"));		
    	
		//re-populate lesson list
    	setListAdapter(getCurrentLessons());
    	itemSelected = -1;
    	getActivity().invalidateOptionsMenu();
    }
     
    public AlertArrayAdapter getCurrentAlerts() {
    	
    	AlertArrayAdapter adapter = new AlertArrayAdapter(getActivity(),
    			R.layout.listview_alert_row, alerts);
    	alerts.clear();
    	File appDir = getActivity().getFilesDir();
    	lessonsForStorage.load(new File(appDir, "lessons.bin"));
    	AlertBehavior alertBehavior = lessonsForStorage.
    	for (String lessonKey : lessonsForStorage) {
    		LessonData lessonData = lessonsForStorage.getLesson(lessonKey);
    		adapter.add(lessonData);
    	}
    	return adapter;
    }
    
       	
}