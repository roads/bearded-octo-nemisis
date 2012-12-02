package com.example.inspirationdraft;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditInspirationFragment extends Fragment {
	
	private InspirationList inspirationsForStorage = new InspirationList();
	private LessonList lessonsForStorage = new LessonList();
	private ArrayList<LessonData> lessonsForDisplay = new ArrayList<LessonData>();
	private String inspirationIdKey;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		inspirationIdKey = getActivity().getIntent().getStringExtra("inspirationIdKey");
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		inspirationsForStorage.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
		
		if (inspirationIdKey != null) {
			InspirationData newInspirationData = inspirationsForStorage.getInspiration(inspirationIdKey);		
			
			TextView inspiration_id = (TextView) getActivity().findViewById(R.id.inspiration_id);			
			inspiration_id.setText(inspirationIdKey);		
			
			EditText content_field = (EditText) getActivity().findViewById(R.id.content_field);			
			content_field.setText(newInspirationData.getContent());	
			
			//for (String lessonKey : lessonsForStorage) {
			//	lessonsForDisplay.add(lessonsForStorage.getLesson(lessonKey));
			//}
			//Integer.parseInt(lessonId)
//			ListView list_checkable_lessons = (ListView) getActivity().findViewById(R.id.lessonlist);
//			ArrayList<String> lessonAssignments = data.getLessonAssignments();
//			if (lessonAssignments.size() > 0) {
//				int itemLocation = 1;
//				//for (String lessonId : lessonAssignments) {
//					CheckableLinearLayout lessonItem = (CheckableLinearLayout) list_checkable_lessons.getChildAt(itemLocation);
//					CheckBox checkbox = lessonItem.findViewById(R.id.check);// setChecked(true);
//				//}
//			}
			
			
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
			InspirationData newInspirationData = null;			
			LessonData newLessonData = null;
			
			EditText content_field = (EditText) getActivity().findViewById(R.id.content_field);			
			String content = content_field.getText().toString();
			
			ListView list_checkable_lessons = (ListView) getActivity().findViewById(R.id.lessonlist);
			int numLessonsInView = list_checkable_lessons.getCount();
			ArrayList<String> newChosenAssignments = new ArrayList<String>();
			SparseBooleanArray chosenLessonsSparseBooleanArray = list_checkable_lessons.getCheckedItemPositions();
			
			for(int i =0; i < numLessonsInView; i++) {
				if(chosenLessonsSparseBooleanArray.get(i) == true) {
					TextView id_field = (TextView) list_checkable_lessons.getChildAt(i).findViewById(R.id.txtLessonId);
					newChosenAssignments.add(id_field.getText().toString());
				}
				
			}
			
			// Update Lessons (by cycling through all lessons and removing and adding as appropriate)
			for (String lessonIdKey:lessonsForStorage) {
				LessonData oldLessonData = lessonsForStorage.getLesson(lessonIdKey);
				newLessonData = new LessonData(oldLessonData.getLessonId(), oldLessonData.getLessonTitle(), oldLessonData.getLessonAssignments());
//				lessonsForStorage.removeLesson(lessonIdKey);
				
				ArrayList<String> oldLessonAssignments = oldLessonData.getLessonAssignments();
				if (newChosenAssignments.contains(lessonIdKey)) {
					// inspiration chose this lesson, add to lesson's assignments
					
					// check if lesson already contains assignment
					if (oldLessonAssignments.contains(lessonIdKey)) {
						// lesson already contains assignment
					} else {
						// lesson does not contain assignment
						newLessonData.addInspirationAssignment(inspirationIdKey);
					}
					
				} else {
					// inspiration did not choose this lesson, remove from lesson's assignments
					// check if lesson contains assignment
					if (oldLessonAssignments.contains(lessonIdKey)) {
						// lesson contains assignment, remove
						newLessonData.removeInspirationAssignment(inspirationIdKey);
					} else {
						// lesson does not contain assignment
					}
					
				}
				lessonsForStorage.addLesson(lessonIdKey, newLessonData);
			}
			lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));
			
			newInspirationData = new InspirationData(content);
			
			if (inspirationIdKey != null) {
				// editing existing
				InspirationData oldData = inspirationsForStorage.getInspiration(inspirationIdKey);
				newInspirationData.setID(oldData.getID());
				newInspirationData.setLessonAssignments(newChosenAssignments);
				inspirationsForStorage.removeID(inspirationIdKey);
			} else {
				// new inspiration
				inspirationIdKey = newInspirationData.getID();
			}
		
			inspirationsForStorage.addInspiration(inspirationIdKey, newInspirationData);
			
			inspirationsForStorage.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
			
			Context context = getActivity();
			CharSequence text = getText(R.string.toast_inspiration_saved);
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_edit_inspiration,container, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_edit_inspiration, menu);
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
		
		for (String lessonKey : lessonsForStorage) {
			lessonsForDisplay.add(lessonsForStorage.getLesson(lessonKey));
		}
        
        ListView list_checked_lessons = (ListView) getActivity().findViewById(R.id.lessonlist);			
		
        LessonArrayAdapterMultiple adapter = new LessonArrayAdapterMultiple(getActivity(),
    			R.layout.listview_lesson_row_multiple, lessonsForDisplay);
		list_checked_lessons.setAdapter(adapter);
		list_checked_lessons.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
}
