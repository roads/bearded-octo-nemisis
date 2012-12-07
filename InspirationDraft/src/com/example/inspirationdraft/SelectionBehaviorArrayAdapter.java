package com.example.inspirationdraft;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SelectionBehaviorArrayAdapter extends ArrayAdapter<SelectionBehaviorData> {

	Context context; 
    int layoutResourceId;    
    ArrayList<SelectionBehaviorData> data = null;
    
    public SelectionBehaviorArrayAdapter(Context context, int layoutResourceId, ArrayList<SelectionBehaviorData> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SelectionBehaviorDataHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new SelectionBehaviorDataHolder();
            holder.selectionBehaviorName = (TextView)row.findViewById(R.id.txtSelectionName);
           
            
            row.setTag(holder);
        }
        else
        {
            holder = (SelectionBehaviorDataHolder)row.getTag();
        }
        
        SelectionBehaviorData lesson = data.get(position);
        holder.selectionBehaviorName.setText(lesson.getSelectionBehaviorName());
        
        return row;
    }
    
    static class SelectionBehaviorDataHolder
    {
    	TextView selectionBehaviorName;
    }
}

