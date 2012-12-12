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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditContentFragment extends Fragment {

	private IdGeneratorList idGeneratorsForStorage = new IdGeneratorList();
	private LessonList lessonsForStorage = new LessonList();
	private InspirationList inspirationsForStorage = new InspirationList();
	private ArrayList<InspirationData> inspirationsForDisplay = new ArrayList<InspirationData>();
	private String lessonIdKey;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		lessonIdKey = getActivity().getIntent().getStringExtra("lessonIdKey");
		inspirationsForStorage.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		idGeneratorsForStorage.load(new File(getActivity().getFilesDir(), "idgenerators.bin"));
		lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
		inspirationsForStorage.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
		
		
		if (lessonIdKey != null) {
			LessonData newLessonData = lessonsForStorage.getLesson(lessonIdKey);
			
			TextView lesson_id = (TextView) getActivity().findViewById(R.id.lesson_id);			
			lesson_id.setText(lessonIdKey);
			
			EditText suffixName_field = (EditText) getActivity().findViewById(R.id.suffix_name_field);
			suffixName_field.setText(newLessonData.getLessonTitle());
			
			// Awful code to pre-check the CheckedTextView (using setChecked inside adapter does not work!!!)
			//http://stackoverflow.com/questions/7202581/setting-listview-item-checked-from-adapter
			//http://stackoverflow.com/questions/12641529/unable-to-check-uncheck-checkedtextview-inside-getview
			ListView list_checkable_inspirations = (ListView) getActivity().findViewById(R.id.inspirationlistmultiple);
			ArrayList<String> inspirationAssignments = newLessonData.getInspirationAssignments();
			if (inspirationAssignments.size() > 0) {
				int itemLocation = 0;
				for (String inspirationId : inspirationsForStorage) {
					if (inspirationAssignments.contains(inspirationId)) {
						list_checkable_inspirations.setItemChecked(itemLocation, true);
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
			
			LessonData lessonData = null;
			
			EditText suffixName_field = (EditText) getActivity().findViewById(R.id.suffix_name_field);
			String suffixName = suffixName_field.getText().toString();
			
			ArrayList<String> newChosenInspirationAssignments = getNewChosenInspirationAssignments(); 
			
			if (lessonIdKey != null) {
				// editing existing
				lessonData = lessonsForStorage.getLesson(lessonIdKey);
				lessonData.setLessonTitle(suffixName);
				lessonData.setInspirationAssignments(newChosenInspirationAssignments);
				lessonsForStorage.removeLesson(lessonIdKey);
			} else {
				// new lesson
				IdGenerator LessonIdGenerator = idGeneratorsForStorage.getIdGenerator((String) getText(R.string.lesson_id_generator));
				lessonIdKey = LessonIdGenerator.getUniqueId();
				lessonData = new LessonData(lessonIdKey, suffixName);
				
				getActivity().getIntent().putExtra("lessonIdKey", lessonIdKey);
				
				lessonData.setInspirationAssignments(newChosenInspirationAssignments);
								
				idGeneratorsForStorage.removeIdGenerator((String) getText(R.string.lesson_id_generator));
				idGeneratorsForStorage.addIdGenerator((String) getText(R.string.lesson_id_generator), LessonIdGenerator);
				idGeneratorsForStorage.save(new File(getActivity().getFilesDir(), "idgenerators.bin"));
			}
		
			lessonsForStorage.addLesson(lessonIdKey, lessonData);
			lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));
			
			updateInspirations(newChosenInspirationAssignments);
			
			Context context = getActivity();
			CharSequence text = getText(R.string.toast_lesson_saved);
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_edit_content,container, false);
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
		
		for (String inspirationKey : inspirationsForStorage) {
			inspirationsForDisplay.add(inspirationsForStorage.getInspiration(inspirationKey));
		}
        
        ListView list_checked_inspirations = (ListView) getActivity().findViewById(R.id.inspirationlistmultiple);			
		
        InspirationArrayAdapterMultiple adapter = new InspirationArrayAdapterMultiple(getActivity(),
    			R.layout.listview_inspiration_row_multiple, inspirationsForDisplay);
		list_checked_inspirations.setAdapter(adapter);
		list_checked_inspirations.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	private ArrayList<String> getNewChosenInspirationAssignments(){
		
		// Determine which inspirations were selected
		ListView list_checkable_inspirations = (ListView) getActivity().findViewById(R.id.inspirationlistmultiple);
		int numInspirationsInView = list_checkable_inspirations.getChildCount();
		ArrayList<String> newChosenInspirations = new ArrayList<String>();
		SparseBooleanArray chosenInspirationsSparseBooleanArray = list_checkable_inspirations.
				getCheckedItemPositions();
		
		for(int i =0; i < numInspirationsInView; i++) {
			if(chosenInspirationsSparseBooleanArray.get(i) == true) {
			//if(list_checkable_inspirations.isItemChecked(i)) {
				TextView id_field = (TextView) list_checkable_inspirations.getChildAt(i).
						findViewById(R.id.txtInspirationId);
				newChosenInspirations.add(id_field.getText().toString());
			}	
		}
				
		return newChosenInspirations;
	} 

	private void updateInspirations(ArrayList<String> newChosenInspirations) {
		InspirationData newInspirationData = null;
		// Update Inspirations (by cycling through all inspirations and removing and adding as appropriate)
		for (String inspirationIdKey:inspirationsForStorage) {
			InspirationData oldInspirationData = inspirationsForStorage.getInspiration(inspirationIdKey);
			newInspirationData = new InspirationData(oldInspirationData.getInspirationId(), oldInspirationData.
					getInspirationContent(), oldInspirationData.getLessonAssignments());
			newInspirationData.removeLessonAssignment(lessonIdKey);
			if (newChosenInspirations.contains(inspirationIdKey)) {
				newInspirationData.addLessonAssignment(lessonIdKey);
			}
			inspirationsForStorage.addInspiration(inspirationIdKey, newInspirationData);
		}
		inspirationsForStorage.save(new File(getActivity().getFilesDir(), "inspirations.bin"));		
	}

}
