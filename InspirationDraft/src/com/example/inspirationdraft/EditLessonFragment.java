package com.example.inspirationdraft;

import java.io.File;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class EditLessonFragment extends Fragment {

	private LessonList lessons = new LessonList();
	private String lessonName;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		lessonName = getActivity().getIntent().getStringExtra("lessonName");
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		lessons.load(new File(getActivity().getFilesDir(), "lessons.bin"));
		
		if (lessonName != null) {
			LessonData data = lessons.getLesson(lessonName);
			
			EditText suffixName_field = (EditText) getActivity().findViewById(R.id.suffix_name_field);
			
			suffixName_field.setText(data.getLessonName());
			
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
			LessonData data = null;
			
			EditText suffixName_field = (EditText) getActivity().findViewById(R.id.suffix_name_field);
			
			String suffixName = suffixName_field.getText().toString();
			
			data = new LessonData(suffixName);
			
			if (lessonName != null) {
				// editing existing
				lessons.removeLesson(lessonName);
			} else {
				// new lesson
				suffixName = data.getLessonName();
				lessonName = "Lesson " + data.getLessonID() + ":  " + suffixName;
			}
		
			lessons.addLesson(lessonName, data);
			
			lessons.save(new File(getActivity().getFilesDir(), "lessons.bin"));
			
			Context context = getActivity();
			CharSequence text = getText(R.string.toast_lesson_saved);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_edit_lesson,container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_edit_lesson, menu);
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
}
