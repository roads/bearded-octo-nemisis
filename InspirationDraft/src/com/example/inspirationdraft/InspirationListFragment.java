package com.example.inspirationdraft;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class InspirationListFragment extends ListFragment {
	
	private InspirationList inspirationsForStorage = new InspirationList();
	private ArrayList<InspirationData> inspirationsForDisplay = new ArrayList<InspirationData>();
	private LessonList lessonsForStorage = new LessonList();
	private int itemSelected = -1;
	
	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(getCurrentInspirations());
	}

	@Override
	public void onPause() {
		super.onPause();
		inspirationsForStorage.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
	}
	
	@Override
	public void onCreate (Bundle savedInstancesState) {
		super.onCreate(savedInstancesState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// menu with new, delete, and edit options
		inflater.inflate(R.menu.fragment_inspirationlist,menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem delete = menu.findItem(R.id.menu_delete_inspiration);
		MenuItem edit = menu.findItem(R.id.menu_edit_inspiration);
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
        Intent intent = new Intent(getActivity(), EditInspirationActivity.class);
		if (item.getItemId() == R.id.menu_new_inspiration) {
	        getActivity().startActivity(intent);			
		}
		if (item.getItemId() == R.id.menu_edit_inspiration) {
			String selectedInspirationId = inspirationsForDisplay.get(itemSelected).getInspirationId();
			intent.putExtra("inspirationIdKey", selectedInspirationId);
	        getActivity().startActivity(intent);
	        itemSelected = -1;
    			getActivity().invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_delete_inspiration) {
			String selectedInspirationId = inspirationsForDisplay.get(itemSelected).getInspirationId();
			remove_id(selectedInspirationId);			
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
        setEmptyText(getText(R.string.empty_inspirationlist));
        setListAdapter(getCurrentInspirations());
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);        
    }
    
    public void remove_id(String inspirationIdKey) {
    	// remove inspiration and save new inspiration list
    	inspirationsForStorage.removeID(inspirationIdKey);
    	inspirationsForStorage.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
    	
    	//update lessons to reflect removal and save
    	lessonsForStorage.load(new File(getActivity().getFilesDir(), "lessons.bin"));
    	LessonData lessonData = null;
    	for (String lessonIdKey:lessonsForStorage) {
			lessonData = lessonsForStorage.getLesson(lessonIdKey);
			lessonData.removeInspirationAssignment(inspirationIdKey);
			lessonsForStorage.addLesson(lessonIdKey, lessonData);
		}
		lessonsForStorage.save(new File(getActivity().getFilesDir(), "lessons.bin"));	
		
    	//re-populate inspiration list
    	setListAdapter(getCurrentInspirations());
    	itemSelected = -1;
    	getActivity().invalidateOptionsMenu();
    }
    
    public InspirationArrayAdapter getCurrentInspirations() {
    	
    	InspirationArrayAdapter adapter = new InspirationArrayAdapter(getActivity(), 
    			R.layout.listview_inspiration_row, inspirationsForDisplay);
    	inspirationsForStorage.clear();
    	inspirationsForDisplay.clear();
    	File appDir = getActivity().getFilesDir();
    	inspirationsForStorage.load(new File(appDir, "inspirations.bin"));
    	for (String inspirationKey : inspirationsForStorage) {
    		InspirationData inspirationData = inspirationsForStorage.getInspiration(inspirationKey);		
    		adapter.add(inspirationData);
    	}
    	return adapter;
    }
    
         	
}