package com.example.inspirationdraft;

import java.io.File;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditInspirationFragment extends Fragment {
	
	private InspirationList inspirations = new InspirationList();
	private String id;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		id = getActivity().getIntent().getStringExtra("id");
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		inspirations.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
		
		if (id != null) {
			InspirationData data = inspirations.getID(id);
			
			EditText content_field = (EditText) getActivity().findViewById(R.id.content_field);
			
			content_field.setText(data.getContent());
			
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (saveData) {
			
			InspirationData data = null;
			
			EditText content_field = (EditText) getActivity().findViewById(R.id.content_field);
			
			String content = content_field.getText().toString();
			
			if (id != null) {
				inspirations.removeID(id);
			}
			
			data = new InspirationData(content);
			inspirations.addInspiration(id, data);
			
			inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
		
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
}
