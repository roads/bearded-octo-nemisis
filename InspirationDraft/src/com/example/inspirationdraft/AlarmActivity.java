package com.example.inspirationdraft;

import java.io.File;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class AlarmActivity extends Activity {

	private String lessonIdKey;
	private LessonList lessonsForStorage = new LessonList();
	private InspirationList inspirationsForStorage = new InspirationList();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        lessonIdKey = getIntent().getStringExtra("lessonIdKey");
        
        lessonsForStorage.load(new File(getFilesDir(), "lessons.bin"));
		inspirationsForStorage.load(new File(getFilesDir(), "inspirations.bin"));
		
		LessonData lessonData = lessonsForStorage.getLesson(lessonIdKey);
		String nextInspirationId = lessonData.getNextInspiration();
		
		lessonsForStorage.removeLesson(lessonIdKey);
		lessonsForStorage.addLesson(lessonIdKey, lessonData);
		lessonsForStorage.save(new File(getFilesDir(), "lessons.bin"));
		
		String textOutput = null;
		if (!nextInspirationId.equalsIgnoreCase("Empty")) {
			InspirationData inspirationData = inspirationsForStorage.getInspiration(nextInspirationId);
			String inspirationContent = inspirationData.getInspirationContent();
			textOutput = (inspirationContent);	
		} else {
			textOutput = "Whoops! No inspirations assigned for this lesson.";
		}
		
		TextView alarm_title_field = (TextView) findViewById(R.id.alarm_title);			
		alarm_title_field.setText(lessonData.getLessonTitle() + ":");    
		
		TextView alarm_content_field = (TextView) findViewById(R.id.alarm_content);			
		alarm_content_field.setText(textOutput);    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_alarm, menu);
        return true;
    }
    
    @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem ok = menu.findItem(R.id.menu_ok_alarm);
		ok.setEnabled(true);
		return true;
    }
    
    @Override
	public boolean onOptionsItemSelected (MenuItem item) {
		if (item.getItemId() == R.id.menu_ok_alarm) {
			this.finish();
		}
		return true;
	}
}
