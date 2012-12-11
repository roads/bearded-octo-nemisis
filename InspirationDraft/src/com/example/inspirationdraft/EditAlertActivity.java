package com.example.inspirationdraft;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditAlertActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alert);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_alert, menu);
        return true;
    }
}
