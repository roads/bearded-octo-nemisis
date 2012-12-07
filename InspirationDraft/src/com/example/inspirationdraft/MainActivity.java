package com.example.inspirationdraft;

//import java.io.File;
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
//    private InspirationList inspirations = new InspirationList();
//    private LessonList lessons = new LessonList();
    private IdGeneratorList idGeneratorsForStorage = new IdGeneratorList();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the application, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        
        idGeneratorsForStorage.load(new File(getFilesDir(), "idgenerators.bin"));
        // Initiate IdGenerators as necessary
        if (!idGeneratorsForStorage.containsIdGenerator((String) getText(R.string.inspiration_id_generator))) {
        	idGeneratorsForStorage.addIdGenerator((String) getText(R.string.inspiration_id_generator), new IdGenerator((String) getText(R.string.inspiration_id_generator)));
        }	
        if (!idGeneratorsForStorage.containsIdGenerator((String) getText(R.string.lesson_id_generator))) {
        	idGeneratorsForStorage.addIdGenerator((String) getText(R.string.lesson_id_generator), new IdGenerator((String) getText(R.string.lesson_id_generator)));
        }
        idGeneratorsForStorage.save(new File(getFilesDir(), "idgenerators.bin"));
        
        // Create some starter inspirations and lessons
        
//        // Note this overrides any persistent data from the last time the application was run
//        String content1 = "A man who does not read good books has no advantage over the man who can't read them. -Mark Twain";        
//        InspirationData data1 = new InspirationData(content1);
//        String idName1 = data1.getID();
//		inspirations.addInspiration(idName1, data1);
//		
//		String content2 = "Your focus determines your reality.";        
//        InspirationData data2 = new InspirationData(content2);
//        String idName2 = data2.getID();
//		inspirations.addInspiration(idName2, data2);
//
//		String content3 = "If you don't stand for something, you'll fall for anything.";        
//        InspirationData data3 = new InspirationData(content3);
//        String idName3 = data3.getID();
//		inspirations.addInspiration(idName3, data3);
//		
//		String content4 = "Don't complicate it by hesitating.";        
//        InspirationData data4 = new InspirationData(content4);
//        String idName4 = data4.getID();
//		inspirations.addInspiration(idName4, data4);
//		
//		String content5 = "";        
//        InspirationData data5 = new InspirationData(content5);
//        String idName5 = data5.getID();
//		inspirations.addInspiration(idName5, data5);
//		
//		String content6 = "";        
//        InspirationData data6 = new InspirationData(content6);
//        String idName6 = data6.getID();
//		inspirations.addInspiration(idName6, data6);
//		
//		String content7 = "";        
//        InspirationData data7 = new InspirationData(content7);
//        String idName7 = data7.getID();
//		inspirations.addInspiration(idName7, data7);
//		
//		String content8 = "";        
//        InspirationData data8 = new InspirationData(content8);
//        String idName8 = data8.getID();
//		inspirations.addInspiration(idName8, data8);
//		
//		String content9 = "";        
//        InspirationData data9 = new InspirationData(content9);
//        String idName9 = data9.getID();
//		inspirations.addInspiration(idName9, data9);
//		
//		String content10 = "";        
//        InspirationData data10 = new InspirationData(content10);
//        String idName10 = data10.getID();
//		inspirations.addInspiration(idName10, data10);
//		
//		String content11 = "";        
//        InspirationData data11 = new InspirationData(content11);
//        String idName11 = data11.getID();
//		inspirations.addInspiration(idName11, data11);
//		
//		String content12 = "";        
//        InspirationData data12 = new InspirationData(content12);
//        String idName12 = data12.getID();
//		inspirations.addInspiration(idName12, data12);
//		
//		String content13 = "";        
//        InspirationData data13 = new InspirationData(content13);
//        String idName13 = data13.getID();
//		inspirations.addInspiration(idName13, data13);
//		
//		String content14 = "";        
//        InspirationData data14 = new InspirationData(content14);
//        String idName14 = data14.getID();
//		inspirations.addInspiration(idName14, data14);
//		
//		inspirations.save(new File(getFilesDir(), "inspirations.bin"));
//		
//		// Create some starter Lessons
//		String suffixName1 = "Qoutes";        
//        LessonData lessonData1 = new LessonData(suffixName1);
//        String lessonName1 = "Lesson " + lessonData1.getLessonID() + ":  " + lessonData1.getLessonName();
//		lessons.addLesson(lessonName1, lessonData1);
//		
//		lessons.save(new File(getFilesDir(), "lessons.bin"));
			
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
