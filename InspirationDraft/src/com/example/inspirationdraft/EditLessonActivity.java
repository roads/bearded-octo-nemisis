package com.example.inspirationdraft;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;

public class EditLessonActivity extends Activity implements ActionBar.TabListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // layout contains reference to EditInspirationFragment
        setContentView(R.layout.activity_edit_lesson);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // For each of the sections in the application, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_section_content).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_section_time).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_section_selection).setTabListener(this));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	//super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
            		
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	//super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// empty activity menu, fragment menu has the functionality
        getMenuInflater().inflate(R.menu.activity_edit_lesson, menu);
        return true;
    }
    
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
    	
    	if (tab.getPosition() == 0) {
	        // When the given tab is selected, show the tab contents in the container
	        EditContentFragment fragment = new EditContentFragment();	  
	        getFragmentManager().beginTransaction()
	                .replace(R.id.detail, fragment)
	                .commit();
    	} else if (tab.getPosition() == 1) {
    		
	        AlertBehaviorFragment fragment = new AlertBehaviorFragment();	    	
	        getFragmentManager().beginTransaction()
	                .replace(R.id.detail, fragment)
	                .commit();
    	} else {
    		
	        SelectionBehaviorFragment fragment = new SelectionBehaviorFragment();	    	
	        getFragmentManager().beginTransaction()
	                .replace(R.id.detail, fragment)
	                .commit();
    	}	
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
