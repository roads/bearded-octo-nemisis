package com.example.inspirationdraft;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            DetailsFragment details = new DetailsFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}

//import android.os.Bundle;
//import android.widget.TextView;
//import android.app.Activity;
//import android.content.Intent;
//
//public class DetailsActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        
//     // Get the message from the intent
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(LessonListFragment.EXTRA_MESSAGE);
//        
//        // Create the text view
//        TextView textView = new TextView(this);
//        textView.setTextSize(20);
//        textView.setText(message);
//        
//        // Set the text view as the activity layout
//        setContentView(textView);
//        
//    }
//}
