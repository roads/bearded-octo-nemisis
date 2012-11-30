package com.example.inspirationdraft;

import java.util.ArrayList;

import com.arctic.weather.Weather;
import com.arctic.weather.WeatherAdapter;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class InspirationLessonAssignmentsFragment extends ListFragment{

	private Weather weather_data[] = new Weather[]
            {
                new Weather(R.drawable.weather_cloudy, "There", "yup"),
                new Weather(R.drawable.weather_showers, "is", "yup"),
                new Weather(R.drawable.weather_snow, "no", "yup"),
                new Weather(R.drawable.weather_storm, "try","yup"),
                new Weather(R.drawable.weather_sunny, "WIGGLE WIGGLE!!!","yup")
            };
	
	private ArrayList<Weather> fetch = new ArrayList<Weather>();
	private WeatherAdapter adapter;
    
    
	public void onResume() {
		super.onResume();
    }

    @Override
    public void onPause() {
    		super.onPause();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Context context = getActivity();
		CharSequence text = "a toast";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}@Override
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText("Nothing.");
		fetch.add(weather_data[0]);
        fetch.add(weather_data[1]);
        fetch.add(weather_data[2]);
        fetch.add(weather_data[3]);
        fetch.add(weather_data[4]);
		adapter = new WeatherAdapter(getActivity(), 
				R.layout.listview_item_row, fetch);
		setListAdapter(adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
    
    
}
