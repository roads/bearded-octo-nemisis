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
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditInspirationFragment extends Fragment {
	
	private IdGeneratorList idGeneratorsForStorage = new IdGeneratorList();
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
		idGeneratorsForStorage.load(new File(getActivity().getFilesDir(), "idgenerators.bin"));
		inspirationsForStorage.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
		
		if (inspirationIdKey != null) {
			InspirationData newInspirationData = inspirationsForStorage.getInspiration(inspirationIdKey);		
			
			TextView inspiration_id = (TextView) getActivity().findViewById(R.id.inspiration_id);			
			inspiration_id.setText(inspirationIdKey);		
			
			EditText content_field = (EditText) getActivity().findViewById(R.id.content_field);			
			content_field.setText(newInspirationData.getInspirationContent());	
			
			// Awful code to pre-check the CheckedTextView (using setChecked inside adapter does not work!!!)
			//http://stackoverflow.com/questions/7202581/setting-listview-item-checked-from-adapter
			//http://stackoverflow.com/questions/12641529/unable-to-check-uncheck-checkedtextview-inside-getview
			ListView list_checkable_lessons = (ListView) getActivity().findViewById(R.id.lessonlist);
			ArrayList<String> lessonAssignments = newInspirationData.getLessonAssignments();
			if (lessonAssignments.size() > 0) {
				int itemLocation = 0;
				for (String lessonId : lessonsForStorage) {
					if (lessonAssignments.contains(lessonId)) {
						list_checkable_lessons.setItemChecked(itemLocation, true);
					}
					itemLocation++;
				}
			}
			
			
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
			InspirationData newInspirationData = null;
			
			EditText content_field = (EditText) getActivity().findViewById(R.id.content_field);			
			String content = content_field.getText().toString();
			
			ArrayList<String> newChosenLessonAssignments = 
					getNewChosenLessonAssignments(); 
			
			
			
			if (inspirationIdKey != null) {
				// editing existing
				InspirationData oldData = inspirationsForStorage.getInspiration(inspirationIdKey);
				String oldInspirationId = oldData.getInspirationId();
				newInspirationData = new InspirationData(oldInspirationId, content);
				//newInspirationData.setInspirationId(oldData.getInspirationId());
				//newInspirationData.setLessonAssignments(newChosenLessonAssignments);
				inspirationsForStorage.removeID(inspirationIdKey);
			} else {
				// new inspiration
				IdGenerator InspirationIdGenerator = idGeneratorsForStorage.getIdGenerator((String) getText(R.string.inspiration_id_generator));
				String newUniqueInspirationId = InspirationIdGenerator.getUniqueId();
				newInspirationData = new InspirationData(newUniqueInspirationId, content);
				inspirationIdKey = newInspirationData.getInspirationId();
				newInspirationData.setLessonAssignments(newChosenLessonAssignments);
				
				idGeneratorsForStorage.removeIdGenerator((String) getText(R.string.inspiration_id_generator));
				idGeneratorsForStorage.addIdGenerator((String) getText(R.string.inspiration_id_generator), InspirationIdGenerator);
				idGeneratorsForStorage.save(new File(getActivity().getFilesDir(), "idgenerators.bin"));
			}
		
			inspirationsForStorage.addInspiration(inspirationIdKey, newInspirationData);
			inspirationsForStorage.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
			
			updateLessons(newChosenLessonAssignments);
			
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
		
		for (String lessonKey : lessonsForStorage) {
			lessonsForDisplay.add(lessonsForStorage.getLesson(lessonKey));
		}
        
        ListView list_checked_lessons = (ListView) getActivity().findViewById(R.id.lessonlist);			
		
        LessonArrayAdapterMultiple adapter = new LessonArrayAdapterMultiple(getActivity(),
    			R.layout.listview_lesson_row_multiple, lessonsForDisplay);
		list_checked_lessons.setAdapter(adapter);
		list_checked_lessons.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
	}
	
	private ArrayList<String> getNewChosenLessonAssignments(){
		
		// Determine which lessons were selected
		ListView list_checkable_lessons = (ListView) getActivity().findViewById(R.id.lessonlist);
		int numLessonsInView = list_checkable_lessons.getCount();
		ArrayList<String> newChosenLessons = new ArrayList<String>();
		SparseBooleanArray chosenLessonsSparseBooleanArray = list_checkable_lessons.
				getCheckedItemPositions();
		
		for(int i =0; i < numLessonsInView; i++) {
			if(chosenLessonsSparseBooleanArray.get(i) == true) {
				TextView id_field = (TextView) list_checkable_lessons.getChildAt(i).
						findViewById(R.id.txtLessonId);
				newChosenLessons.add(id_field.getText().toString());
			}	
		}
		return newChosenLessons;
	}
	
	private void updateLessons(ArrayList<String> newChosenAssignments) {
		LessonData newLessonData = null;
		// Update Lessons (by cycling through all lessons and removing and adding as appropriate)
		for (String lessonIdKey:lessonsForStorage) {
			LessonData oldLessonData = lessonsForStorage.getLesson(lessonIdKey);
			newLessonData = new LessonData(oldLessonData.getLessonId(), oldLessonData.
					getLessonTitle(), oldLessonData.getInspirationAssignments());			
			newLessonData.removeInspirationAssignment(inspirationIdKey);
			if (newChosenAssignments.contains(lessonIdKey)) {				
				newLessonData.addInspirationAssignment(inspirationIdKey);
			}
			lessonsForStorage.addLesson(lessonIdKey, newLessonData);			
		}
		lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));
	}
}
