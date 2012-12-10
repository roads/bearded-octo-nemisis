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
import android.widget.AbsListView;
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
	int itemSelected = -1;
	
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
		setListAdapter(getAlertAdaptor());
	}

	@Override
	public void onPause() {
		super.onPause();
		if(saveData) {
			AlertData newAlertData = null;
			
			
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
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem delete = menu.findItem(R.id.menu_delete_alert);
		MenuItem edit = menu.findItem(R.id.menu_edit_alert);
		MenuItem new_ = menu.findItem(R.id.menu_new_alert);
		if (itemSelected != -1) {
			delete.setEnabled(true);
			edit.setEnabled(true);
			new_.setEnabled(true);
		} else {
			delete.setEnabled(false);
			edit.setEnabled(false);
			new_.setEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		if(item.getItemId() == R.id.menu_edit_alert){
			
			EditAlertFragment fragment = new EditAlertFragment();	  
	        getFragmentManager().beginTransaction()
	                .replace(R.id.container, fragment)
	                .commit();
		}else if (item.getItemId() == R.id.menu_new_alert){
			EditAlertFragment fragment = new EditAlertFragment();	  
	        getFragmentManager().beginTransaction()
	                .replace(R.id.container, fragment)
	                .commit();
		}
		return true;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelected = position;
		getActivity().invalidateOptionsMenu();
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(getAlertAdaptor());
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);     
    }
	
     
    public AlertArrayAdapter getAlertAdaptor() {
    	
    	AlertArrayAdapter adapter = new AlertArrayAdapter(getActivity(),
    			R.layout.listview_alert_row, myAlerts.getAlertArrayList());
    	return adapter;
    }
    
    public AlertBehavior getCurrentAlertBehavior(){
    	//get size of list
    	//loop through list and get view
    	return new AlertBehavior();
    }
    
       	
}