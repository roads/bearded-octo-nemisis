package com.example.inspirationdraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class MasterListFragment extends ListFragment {
	
	private InspirationList inspirations = new InspirationList();
	private int itemSelected = -1;
	
	public void onResume() {
		super.onResume();
		setListAdapter(getCurrentInspirations());
	}

	public void onPause() {
		super.onPause();
		inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
	}
	
	public void onCreate (Bundle savedInstancesState) {
		super.onCreate(savedInstancesState);
		setHasOptionsMenu(true);
	}
	
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
//    		Bundle savedInstanceState) {
//        
//    	// Inflate the layout for this fragment
//    	return inflater.inflate(R.layout.fragment_masterlist, container, false);
//    }
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_masterlist,menu);
	}
	
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem delete = menu.fineItem(R.id.menu_delete_inspiration);
		MenuItem edit = menu.findItem(R.id.menu_edit_inspiration);
		if (itemSelected != -1) {
			delete.setEnabled(true);
			edit.setEnabled(true);
		} else {
			delete.setEnabled(false);
			edit.setEnabled(false);
		}
	}
	
	public boolean onOptionsItemSelected (MenuItem item) {
        Intent intent = new Intent(getActivity(), EditInspiration.class);
		if (item.getItemId() == R.id.menu_new_inspiration) {
	        getActivity().startActivity(intent);			
		}
		if (item.getItemId() == R.id.menu_edit_inspiration) {
			TextView text = (TextView) getListView().getChildAt(itemSelected);
			intent.putExtra("content", text.getText().toString());
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
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelected = position;
		getActivity().invalidateOptionsMenu();
	}
	
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("No Inspirations");
        setListAdapter(getCurrentInspirations());
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);   
    }
	
    public void remove_id(Integer id) {
    	inspirations.removeID(id);
    	inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
    	setListAdapter(getCurrentInspirations());
    	itemSelected = -1;
    	getActivity().invalidateOptionsMenu();
    }
    
    public ArrayAdapter<String> getCurrentInspirations() {
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice);
    	inspirations.clear();
    	File appDir = getActivity().getFilesDir();
    	inspirations.load(new File(appDir, "profiles.bin"));
    	for (String content : inspirations) {
    		adapter.add(content);
    	}
    	return adapter;
    }
     	
}