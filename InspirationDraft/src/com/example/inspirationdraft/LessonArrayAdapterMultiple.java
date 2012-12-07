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

public class LessonArrayAdapterMultiple extends ArrayAdapter<LessonData>{

	Context context; 
    int layoutResourceId;    
    ArrayList<LessonData> data = null;
    String inspirationIdKey;
    
    public LessonArrayAdapterMultiple(Context context, int layoutResourceId, ArrayList<LessonData> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LessonDataHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new LessonDataHolder();
            holder.lessonID = (TextView)row.findViewById(R.id.txtLessonId);
            holder.lessonTitle = (CheckedTextView)row.findViewById(R.id.txtLessonTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (LessonDataHolder)row.getTag();
        }
        
        LessonData lesson = data.get(position);
        
        holder.lessonID.setText(lesson.getLessonId());
        holder.lessonTitle.setText(lesson.getLessonTitle());
        return row;
    }
    
    static class LessonDataHolder
    {
    	TextView lessonID;
    	CheckedTextView lessonTitle;        
    }
}
