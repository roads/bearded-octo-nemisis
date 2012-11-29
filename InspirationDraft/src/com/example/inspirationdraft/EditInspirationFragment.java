package com.example.inspirationdraft;

import java.io.File;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditInspirationFragment extends Fragment {
	
	private InspirationList inspirations = new InspirationList();
	private String idKey;
	private boolean saveData = true;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		idKey = getActivity().getIntent().getStringExtra("idKey");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		saveData = true;
		inspirations.load(new File(getActivity().getFilesDir(), "inspirations.bin"));
		
		if (idKey != null) {
			InspirationData data = inspirations.getInspiration(idKey);		
			
			TextView inspiration_id = (TextView) getActivity().findViewById(R.id.inspiration_id);			
			inspiration_id.setText(idKey);		
			
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
			
			data = new InspirationData(content);
			
			if (idKey != null) {
				// editing existing
				InspirationData oldData = inspirations.getInspiration(idKey);
				data.setID(oldData.getID());
				inspirations.removeID(idKey);
			} else {
				// new inspiration
				idKey = data.getID();
			}
		
			inspirations.addInspiration(idKey, data);
			
			inspirations.save(new File(getActivity().getFilesDir(), "inspirations.bin"));
			
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
