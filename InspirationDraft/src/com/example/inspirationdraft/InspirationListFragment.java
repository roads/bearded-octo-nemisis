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

public class InspirationListFragment extends ListFragment {
	
	private InspirationList inspirations = new InspirationList();
	private int itemSelected = -1;
	
	@Override
	public void onResume() {
		super.onResume();
		setListAdapter(getCurrentInspirations());
	}

	@Override
	public void onPause() {
		super.onPause();
		inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
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
			TextView text = (TextView) getListView().getChildAt(itemSelected);
			intent.putExtra("idName", text.getText().toString());
	        getActivity().startActivity(intent);
	        itemSelected = -1;
    			getActivity().invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_delete_inspiration) {
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
        setEmptyText(getText(R.string.empty_inspirationlist));
        setListAdapter(getCurrentInspirations());
  //      ListView inspirationListView = getListView(); // added
//		inspirationListView.setAdapter(getCurrentInspirations());	// added
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
        
    }
    
    public void remove_id(String idName) {
    	inspirations.removeID(idName);
    	inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
    	setListAdapter(getCurrentInspirations());
    	itemSelected = -1;
    	getActivity().invalidateOptionsMenu();
    }
    
//    public InspirationArrayAdapter getCurrentInspirations() {
//    	
//    	InspirationData[] inspirationArray = new InspirationData[14];
//    	for (int inspirationID = 1; inspirationID < 15; inspirationID++) {
//    		inspirationArray[(inspirationID - 1)] = inspirations.getInspiration("" + inspirationID);
//    	}
//    	
//    	InspirationArrayAdapter adapter = new InspirationArrayAdapter(getActivity(), inspirationArray);
//    	return adapter;
//    }
//
    
    public ArrayAdapter<String> getCurrentInspirations() {
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice);
    	inspirations.clear();
    	File appDir = getActivity().getFilesDir();
    	inspirations.load(new File(appDir, "inspirations.bin"));
    	for (String inspirationKey : inspirations) {
    		String text = inspirationKey;
    		//InspirationData inspirationData = inspirations.getInspiration(inspirationKey);
    		//String text = inspirationData.toListFormatting();
    		adapter.add(text);
    	}
    	return adapter;
    }
    
//    public ArrayAdapter<String> getCurrentInspirations() {
//    	
//    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_inspiration_single_choice);
//    	inspirations.clear();
//    	File appDir = getActivity().getFilesDir();
//    	inspirations.load(new File(appDir, "inspirations.bin"));
//    	for (String inspirationKey : inspirations) {
//    		//String text = inspirationKey;
//    		InspirationData inspirationData = inspirations.getInspiration(inspirationKey);
//    		String text = inspirationData.toListFormatting();
//    		adapter.add(text);
//    	}
//    	return adapter;
//    }
    
    
     	
}