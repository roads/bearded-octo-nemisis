package com.example.inspirationdraft;

import java.io.File;
import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

public class AlertBehaviorFragment extends ListFragment {
//	private AlertBehavior alertBehaviorForStorage = new AlertBehavior();
//	private ArrayList<AlertData> alertsForDisplay = new ArrayList<AlertData>();
//	private int itemSelected = -1;
	
	private IdGeneratorList idGeneratorsForStorage = new IdGeneratorList();
	private LessonList lessonsForStorage = new LessonList();
	private AlertBehavior myAlerts = new AlertBehavior();
	private String lessonIdKey;
	private boolean saveData = true;
	int itemSelected = -1;
	private static final String TAG = "AlertBehaviorFragment";
	
	@Override
	public void onCreate (Bundle savedInstancesState) {
		Log.v(TAG, "in Create");
		super.onCreate(savedInstancesState);
		setHasOptionsMenu(true);
		lessonIdKey = getActivity().getIntent().getStringExtra("lessonIdKey");
		idGeneratorsForStorage.load(new File(getActivity().getFilesDir(), "idgenerators.bin"));
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
	}
	
	@Override
	public void onResume() {
		Log.v(TAG, "in Resume");
		super.onResume();
		saveData = true;
		idGeneratorsForStorage.load(new File(getActivity().getFilesDir(), "idgenerators.bin"));
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
		
		if(lessonIdKey != null){
			Log.v(TAG, "lessonIdKey isn't null"+lessonIdKey);
			LessonData newLessonData = lessonsForStorage.getLesson(lessonIdKey);
			ListView list_checkable_alerts = (ListView) getActivity().findViewById(R.id.alertlist);
			myAlerts = newLessonData.getAlerts();
			Log.v(TAG, "past alert assignment"+myAlerts);
			if(myAlerts != null){//myAlerts.getNumberOfAlerts() > 0){
				int itemLocation = 0;
				for (AlertData a : myAlerts){
					if(a.getExists()){
						list_checkable_alerts.setItemChecked(itemLocation, true);
					}
					itemLocation++;
				}
			}
		}
		Log.v(TAG, "here");
		setListAdapter(getAlertAdaptor());
		Log.v(TAG, "here2");
	}

	@Override
	public void onPause() {
		Log.v(TAG, "in onPause");
		super.onPause();
		if(saveData) {
			AlertData newAlertData = null;
			
			
		}
		//alerts.save(new File(getActivity().getFilesDir(), "lessons.bin"));
	}	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(TAG, "in onCreateView");
		return inflater.inflate(R.layout.fragment_alert_behavior,container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.v(TAG, "in onCreateOptionsMenu");
		// menu with new, delete, and edit options
		inflater.inflate(R.menu.fragment_alert_behavior,menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		Log.v(TAG, "in onPrepareOptionsMenu");
		super.onPrepareOptionsMenu(menu);
		MenuItem delete = menu.findItem(R.id.menu_delete_alert);
		MenuItem edit = menu.findItem(R.id.menu_edit_alert);
		MenuItem new_ = menu.findItem(R.id.menu_new_alert);
		MenuItem ok = menu.findItem(R.id.menu_ok);
		MenuItem cancel = menu.findItem(R.id.menu_cancel);
		if (itemSelected != -1) {
			delete.setEnabled(true);
			edit.setEnabled(true);
			new_.setEnabled(false);
			ok.setEnabled(true);
			cancel.setEnabled(true);
		} else {
			delete.setEnabled(false);
			edit.setEnabled(false);
			new_.setEnabled(true);
			ok.setEnabled(true);
			cancel.setEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		Log.v(TAG, "in onOptionsItemSelected");
		Intent intent = new Intent(getActivity(), EditAlertActivity.class);
		if(item.getItemId() == R.id.menu_edit_alert){
			//itemSelected = position in arraylist
			intent.putExtra("alertIndex", itemSelected);
			intent.putExtra("lessonIdKey", lessonIdKey);
			getActivity().startActivity(intent);
			itemSelected = -1;
			getActivity().invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_new_alert){
			intent.putExtra("alertIndex", -1);
			intent.putExtra("lessonIdKey", lessonIdKey);
			getActivity().startActivity(intent);
			itemSelected = -1;
			getActivity().invalidateOptionsMenu();
		}
		if(item.getItemId() == R.id.menu_delete_alert){
			myAlerts.removeByIndex(itemSelected);
			itemSelected = -1;
			getActivity().invalidateOptionsMenu();
		}
		if(item.getItemId() == R.id.menu_ok){
			saveData = true;
			getActivity().finish();
		}else if(item.getItemId() == R.id.menu_cancel){
			saveData = false;
			getActivity().finish();
		}else if(item.getItemId() == android.R.id.home){
			saveData = false;
			getActivity().finish();
		}
		return true;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.v(TAG, "in onListItemClick");
		itemSelected = position;
		getActivity().invalidateOptionsMenu();
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		Log.v(TAG, "in onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        setListAdapter(getAlertAdaptor());
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);     
    }
	
     
    public AlertArrayAdapter getAlertAdaptor() {
    	Log.v(TAG, "in getAlertAdaptor");
    	
    	AlertArrayAdapter adapter = new AlertArrayAdapter(getActivity(),
    			R.layout.listview_alert_row, myAlerts.getAlertArrayList());
//    	myAlerts.clear();
//    	lessonsForStorage.clear();
//    	File appDir = getActivity().getFilesDir();
//    	lessonsForStorage.load(new File(appDir, "lessons.bin"));
//    	LessonData lesson = lessonsForStorage.getLesson(lessonIdKey);
//    	Log.v(TAG, "HEREEEE");
//    	if(lesson.doIHaveAlerts()){
//    		AlertBehavior alertBehave = lesson.getAlerts();
//        	Log.v(TAG, "HEREEEE alert"+ alertBehave);
//        	if(alertBehave != null){
//        		Log.v(TAG, "HO HEREEEE");
//        		ArrayList<AlertData> alertList = alertBehave.getAlertArrayList();
//        		adapter.addAll(alertList);
//        	}
//    	}
    	
    	Log.v(TAG, "HI HEREEEE");
    	
    	return adapter;
    }
    
    public AlertBehavior getCurrentAlertBehavior(){
    	Log.v(TAG, "In getCurrentAlertBehavior");
    	//get size of list
    	//loop through list and get view
    	return new AlertBehavior();
    }
    
       	
}