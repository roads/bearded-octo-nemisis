package com.example.inspirationdraft;

import java.io.File;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AlertBehaviorFragment extends Fragment {

	private LessonList lessonsForStorage = new LessonList();
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
			//
			//
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
			//handle saveing
			//
			//
			//
			
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
		if (item.getItemId() == R.id.menu_new_alert){
			saveData = false;
			getActivity().finish();
		}
		if (item.getItemId() == R.id.menu_delete_alert){
			saveData = false;
			getActivity().finish();
		}
		if (item.getItemId() == R.id.menu_edit_alert){
			saveData = false;
			getActivity().finish();
		}
		
		
		return true;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		//stub
		//
		//
	}
}