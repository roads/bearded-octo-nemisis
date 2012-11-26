package com.example.inspirationdraft;

import android.os.Bundle;
import android.app.Activity;

public class ShakespeareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            ShakespeareFragment details = new ShakespeareFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
}
