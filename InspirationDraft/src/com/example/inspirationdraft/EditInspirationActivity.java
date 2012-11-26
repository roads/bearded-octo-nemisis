package com.example.inspirationdraft;

import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;

public class EditInspirationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout contains reference to EditInspirationFragment
        setContentView(R.layout.activity_edit_inspiration);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// empty activity menu, fragment menu has the functionality
        getMenuInflater().inflate(R.menu.activity_edit_inspiration, menu);
        return true;
    }


}
