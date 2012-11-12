package com.example.inspirationdraft;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class LessonListFragment extends ListFragment {
	
	int mCurCheckPosition = 0;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));
        
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

    }
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //showDetails(position);
    }
    
    //void showDetails(int index) {
    //    mCurCheckPosition = index;
    //    Intent intent = new Intent();
    //    intent.setClass(getActivity(), DetailsActivity.class);
    //    intent.putExtra("index", index);
    //    startActivity(intent);
    //}
        
        
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
    		Bundle savedInstanceState) {
        
    	// Inflate the layout for this fragment
    	return inflater.inflate(R.layout.fragment_lessonlist, container, false);
    }
	
	
}