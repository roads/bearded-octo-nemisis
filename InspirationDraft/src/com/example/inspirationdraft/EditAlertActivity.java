package com.example.inspirationdraft;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditAlertActivity extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout contains reference to EditInspirationFragment
        setContentView(R.layout.activity_edit_alert);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// empty activity menu, fragment menu has the functionality
        getMenuInflater().inflate(R.menu.fragment_cancel_save, menu);
        return true;
    }

}
