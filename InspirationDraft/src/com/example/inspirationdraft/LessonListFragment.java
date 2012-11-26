package com.example.inspirationdraft;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class LessonListFragment extends ListFragment {
	
	private LessonList lessons = new LessonList();
	private int itemSelected = -1;
	
	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(getCurrentLessons());
	}

	@Override
	public void onPause() {
		super.onPause();
		lessons.save(new File(getActivity().getFilesDir(), "lessons.bin"));
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
			TextView text = (TextView) getListView().getChildAt(itemSelected);
			intent.putExtra("lessonName", text.getText().toString());
	        getActivity().startActivity(intent);
	        itemSelected = -1;
    			getActivity().invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_delete_lesson) {
			TextView text = (TextView) getListView().getChildAt(itemSelected);
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
        setEmptyText("No Lessons");
        setListAdapter(getCurrentLessons());
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
        
    }
    
    public void remove_id(String lessonName) {
    	lessons.removeLesson(lessonName);
    	lessons.save(new File(getActivity().getFilesDir(), "lessons.bin"));
    	setListAdapter(getCurrentLessons());
    	itemSelected = -1;
    	getActivity().invalidateOptionsMenu();
    }
    
//    public TwoLineArrayAdapter getCurrentLessons() {
//    	
//    }
//    
    public ArrayAdapter<String> getCurrentLessons() {
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice);
    	lessons.clear();
    	File appDir = getActivity().getFilesDir();
    	lessons.load(new File(appDir, "lessons.bin"));
    	for (String lessonName : lessons) {
    		adapter.add(lessonName);
    	}
    	return adapter;
    }
    
    
     	
}
//import android.app.ListFragment;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//
//public class LessonListFragment extends ListFragment {
//	
//	int mCurCheckPosition = 0;
////	private int itemSelected = -1;
//	
//	@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
//    		Bundle savedInstanceState) {
//        
//    	// Inflate the layout for this fragment
//    	return inflater.inflate(R.layout.fragment_lessonlist, container, false);
//    }
//	
//	
//	@Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        
//        // Populate list with our static array of titles.
//        setListAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));
//               
//        
//    }
//	
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        showDetails(position);
//    }
//    
//    void showDetails(int index) {
//        mCurCheckPosition = index;
//        
//        // launch a new activity to display
//        // the dialog fragment with selected text.
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), ShakespeareActivity.class);
//        intent.putExtra("index", index);
//        startActivity(intent);
//
//    }
//        	
//}