package com.example.inspirationdraft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class SimpleAlarm extends BroadcastReceiver {
	
	private String lessonIdKey = "-1";
	
	public SimpleAlarm() {
		super();
	}
	
     @Override
     public void onReceive(Context context, Intent intent) {   
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | 
        	 PowerManager.ON_AFTER_RELEASE, "");
        wl.acquire();

        Intent myIntent = new Intent(context, AlarmActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        lessonIdKey = intent.getStringExtra("lessonIdKey");
        myIntent.putExtra("lessonIdKey", lessonIdKey);
        context.startActivity(myIntent);
        
        wl.release();
     }

}