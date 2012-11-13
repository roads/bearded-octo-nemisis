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

public class InspirationDetailFragment extends Fragment {
	
	private InspirationList inspirations = new InspirationList();
	private Integer id;
	private boolean saveData = true;
	
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		id = getActivity().getIntent().getIntExtra("id", 1);

	}
	
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_edit_inspiration,container, false);
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_edit_inspiration, menu);
	}
	
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == R.id.menu_ok) {
			saveData = true;
		} else if (item.getItemId() == R.id.menu_cancel) {
			saveData = false;
		}
		getActivity().finish();
		return true;
	}
}
