package com.example.inspirationdraft;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

public class SimpleAlarm extends BroadcastReceiver {
	
	private String lessonIdKey = "-1";
	private LessonList lessonsForStorage = new LessonList();
	private InspirationList inspirationsForStorage = new InspirationList();
	
	public SimpleAlarm() {
		super();
	}
	
     @Override
     public void onReceive(Context context, Intent intent) {   
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | 
        	 PowerManager.ACQUIRE_CAUSES_WAKEUP, "");
        wl.acquire();

		// Put here YOUR code.
		lessonsForStorage.load(new File(context.getFilesDir(), "lessons.bin"));
		inspirationsForStorage.load(new File(context.getFilesDir(), "inspirations.bin"));
		lessonIdKey = intent.getStringExtra("lessonIdKey");
		LessonData lessonData = lessonsForStorage.getLesson(lessonIdKey);
		String nextInspirationId = lessonData.getNextInspiration();
		lessonData = lessonsForStorage.getLesson(lessonIdKey);
		lessonsForStorage.removeLesson(lessonIdKey);
		lessonsForStorage.addLesson(lessonIdKey, lessonData);
		lessonsForStorage.save(new File(context.getFilesDir(), "lessons.bin"));
		
		String textOutput = null;
		if (!nextInspirationId.equalsIgnoreCase("Empty")) {
			InspirationData inspirationData = inspirationsForStorage.getInspiration(nextInspirationId);
			String inspirationContent = inspirationData.getInspirationContent();
			textOutput = (lessonIdKey + ":  " + inspirationContent);	
		} else {
			textOutput = "Whoops! No inspirations assigned for this lesson.";
		}
			 		
        Toast.makeText(context, textOutput, Toast.LENGTH_LONG).show();

        wl.release();
     }

//     public void SetAlarm(Context context) {
//     AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//     Intent i = new Intent(context, SimpleAlarm.class);
//     PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//     am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pi);
//     //am.setRepeating(type, triggerAtMillis, intervalMillis, operation)
//     //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 15 , pi); // Millisec * Second * Minute
// }
//
// 	public void CancelAlarm(Context context) {
//     Intent intent = new Intent(context, SimpleAlarm.class);
//     PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//     AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//     alarmManager.cancel(sender);
// 	}

}