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

public class InspirationListFragment extends ListFragment {
	
	private InspirationList inspirations = new InspirationList();
	private ArrayList<InspirationData> fetch = new ArrayList<InspirationData>();
	//private InspirationArrayAdapter adapter;

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
			TextView text = (TextView) getListView().getChildAt(itemSelected).findViewById(R.id.txtInspirationID);
			intent.putExtra("idKey", text.getText().toString());
	        getActivity().startActivity(intent);
	        itemSelected = -1;
    			getActivity().invalidateOptionsMenu();
		}
		if (item.getItemId() == R.id.menu_delete_inspiration) {
			TextView text = (TextView) getListView().getChildAt(itemSelected).findViewById(R.id.txtInspirationID);
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
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
        
    }
    
    public void remove_id(String idKey) {
    	inspirations.removeID(idKey);
    	inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
    	setListAdapter(getCurrentInspirations());
    	itemSelected = -1;
    	getActivity().invalidateOptionsMenu();
    }
    
    public InspirationArrayAdapter getCurrentInspirations() {
    	
    	InspirationArrayAdapter adapter = new InspirationArrayAdapter(getActivity(), R.layout.inspiration_listview_item_row, fetch);
    	inspirations.clear();
    	fetch.clear();
    	File appDir = getActivity().getFilesDir();
    	inspirations.load(new File(appDir, "inspirations.bin"));
    	for (String inspirationKey : inspirations) {
    		InspirationData inspirationData = inspirations.getInspiration(inspirationKey);		
    		adapter.add(inspirationData);
    	}
    	return adapter;
    }
    
    
     	
}