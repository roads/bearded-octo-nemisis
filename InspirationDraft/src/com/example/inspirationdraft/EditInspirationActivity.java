package com.example.inspirationdraft;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditInspirationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inspiration);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_inspiration, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
