package com.example.inspirationdraft;

import java.io.File;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements ActionBar.TabListener {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private InspirationList inspirations = new InspirationList();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        
        // Create some starter inspirations 
        // Note this overrides any persistent data from the last time the application was run
        String content1 = "He who doesn't read is no better than one who can't read.";        
        InspirationData data1 = new InspirationData(content1);
        String idName1 = data1.getID();
		inspirations.addInspiration(idName1, data1);
		
		String content2 = "Your focus determines your reality.";        
        InspirationData data2 = new InspirationData(content2);
        String idName2 = data2.getID();
		inspirations.addInspiration(idName2, data2);

		String content3 = "If you don't stand for something, you'll fall for anything.";        
        InspirationData data3 = new InspirationData(content3);
        String idName3 = data3.getID();
		inspirations.addInspiration(idName3, data3);
		
		inspirations.save(new File(getFilesDir(), "inspirations.bin"));
	
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
    	// an empty menu
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
    	
    	if (tab.getPosition() == 0) {
	        // When the given tab is selected, show the tab contents in the container
	        LessonListFragment fragment = new LessonListFragment();	  
	        getFragmentManager().beginTransaction()
	                .replace(R.id.container, fragment)
	                .commit();
    	} else {
    		
	        Fragment fragment = new InspirationListFragment();	    	
	        getFragmentManager().beginTransaction()
	                .replace(R.id.container, fragment)
	                .commit();
    	}	
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
