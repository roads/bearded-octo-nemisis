package com.example.inspirationdraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
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
	
		myAlerts.add(new AlertData("in 7 seconds", ""));
		myAlerts.add(new AlertData("02", "05"));
		myAlerts.add(new AlertData("02", "10"));
		myAlerts.add(new AlertData("09", "30"));
		
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
	
		if (selectedAlertBehavior.contains(0)) {
			Intent intent = new Intent(getActivity(), SimpleAlarm.class);
		    intent.putExtra("lessonIdKey", lessonIdKey);
		    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
		    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+7000, pendingIntent);		
		}
		if (selectedAlertBehavior.contains(1)) {
			Calendar timeOff = Calendar.getInstance();
			//int days = Calendar.WEDNESDAY + (7 - timeOff.get(Calendar.DAY_OF_WEEK)); // how many days until Sunday
			timeOff.add(Calendar.DATE, 0);
			timeOff.set(Calendar.HOUR, 14);
			timeOff.set(Calendar.MINUTE, 5);
			timeOff.set(Calendar.SECOND, 0);
			
			Intent intent2 = new Intent(getActivity(), SimpleAlarm.class);
		    intent2.putExtra("lessonIdKey", lessonIdKey);
		    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getActivity(), 2, intent2, 0);
		    AlarmManager alarmManager2 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		    alarmManager2.set(AlarmManager.RTC_WAKEUP,  timeOff.getTimeInMillis(), pendingIntent2);		
		}
		if (selectedAlertBehavior.contains(2)) {
			Calendar timeOff = Calendar.getInstance();
			//int days = Calendar.WEDNESDAY + (7 - timeOff.get(Calendar.DAY_OF_WEEK)); // how many days until Sunday
			timeOff.add(Calendar.DATE, 0);
			timeOff.set(Calendar.HOUR, 14);
			timeOff.set(Calendar.MINUTE, 10);
			timeOff.set(Calendar.SECOND, 0);
			
			Intent intent3 = new Intent(getActivity(), SimpleAlarm.class);
		    intent3.putExtra("lessonIdKey", lessonIdKey);
		    PendingIntent pendingIntent3 = PendingIntent.getBroadcast(getActivity(), 3, intent3, 0);
		    AlarmManager alarmManager3 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		    alarmManager3.set(AlarmManager.RTC_WAKEUP,  timeOff.getTimeInMillis(), pendingIntent3);		
		}
		
		if (selectedAlertBehavior.contains(3)) {
			Calendar timeOff = Calendar.getInstance();
			//int days = Calendar.WEDNESDAY + (7 - timeOff.get(Calendar.DAY_OF_WEEK)); // how many days until Sunday
			timeOff.add(Calendar.DATE, 0);
			timeOff.set(Calendar.HOUR, 9);
			timeOff.set(Calendar.MINUTE, 30);
			timeOff.set(Calendar.SECOND, 0);
			
			Intent intent4 = new Intent(getActivity(), SimpleAlarm.class);
		    intent4.putExtra("lessonIdKey", lessonIdKey);
		    PendingIntent pendingIntent4 = PendingIntent.getBroadcast(getActivity(), 4, intent4, 0);
		    AlarmManager alarmManager4 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		    alarmManager4.set(AlarmManager.RTC_WAKEUP,  timeOff.getTimeInMillis(), pendingIntent4);		
		}
//	 	public void CancelAlarm(Context context) {
//	     Intent intent = new Intent(context, SimpleAlarm.class);
//	     PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//	     AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//	     alarmManager.cancel(sender);
//	 	}
		
	}
	
}