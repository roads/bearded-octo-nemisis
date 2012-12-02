package com.example.inspirationdraft;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class LessonListFragment extends ListFragment {
	
	private LessonList lessonsForStorage = new LessonList();
	private ArrayList<LessonData> lessonsForDisplay = new ArrayList<LessonData>();
	private InspirationList inspirationsForStorage = new InspirationList();
	private int itemSelected = -1;
	
	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(getCurrentLessons());
	}

	@Override
	public void onPause() {
		super.onPause();
		lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));
	}
	
	@Override
	public void onCreate (Bundle savedInstancesState) {
		super.onCreate(savedInstancesState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// menu with new, delete, and edit options
		inflater.inflate(R.menu.fragment_lessonlist,menu);
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
	public boolean onOptionsItemSelected (MenuItem item) {
        Intent intent = new Intent(getActivity(), EditLessonActivity.class);
		if (item.getItemId() == R.id.menu_new_lesson) {
	        getActivity().startActivity(intent);			
		}
		if (item.getItemId() == R.id.menu_edit_lesson) {
			TextView text = (TextView) getListView().getChildAt(itemSelected).
					findViewById(R.id.txtLessonId);
			intent.putExtra("lessonIdKey", text.getText().toString());
	        getActivity().startActivity(intent);
	        itemSelected = -1;
    			getActivity().invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_delete_lesson) {
			TextView text = (TextView) getListView().getChildAt(itemSelected).
					findViewById(R.id.txtLessonId);
			remove_id(text.getText().toString());
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
        setEmptyText(getText(R.string.empty_lessonlist));
        setListAdapter(getCurrentLessons());
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);       
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
     
    public LessonArrayAdapter getCurrentLessons() {
    	
    	LessonArrayAdapter adapter = new LessonArrayAdapter(getActivity(),
    			R.layout.listview_lesson_row, lessonsForDisplay);
    	lessonsForStorage.clear();
    	lessonsForDisplay.clear();
    	File appDir = getActivity().getFilesDir();
    	lessonsForStorage.load(new File(appDir, "lessons.bin"));
    	for (String lessonKey : lessonsForStorage) {
    		LessonData lessonData = lessonsForStorage.getLesson(lessonKey);
    		adapter.add(lessonData);
    	}
    	return adapter;
    }
    
       	
}