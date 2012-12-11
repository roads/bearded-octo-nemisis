package com.example.inspirationdraft;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class AlertArrayAdapter extends ArrayAdapter<AlertData> {
	
	Context context;
	int layoutResourceId;
	ArrayList<AlertData> data = null;
	String alertIdKey;

	public AlertArrayAdapter(Context context, int layoutResourceId, ArrayList<AlertData> data) {
		super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
	}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AlertDataHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new AlertDataHolder();
            
            holder.time = (CheckedTextView)row.findViewById(R.id.alert_time_picker);
            //holder.days = (TextView)row.findViewById(R.id.txtLessonId);

            row.setTag(holder);
        }
        else
        {
            holder = (AlertDataHolder)row.getTag();
        }
        
        AlertData alert = data.get(position);
        String theTime = (alert.getHour() +  ":" + alert.getMinute());
        holder.time.setText(theTime);
        //holder.time.setText((CharSequence) alert.getTime());
        //holder.days.setText(alert.getDays());
        return row;
    }
    
    static class AlertDataHolder
    {
    	CheckedTextView time;
    	//TextView days;
    }
}
