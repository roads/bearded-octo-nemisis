package com.example.inspirationdraft;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LessonArrayAdapter extends ArrayAdapter<LessonData>{

	Context context; 
    int layoutResourceId;    
    ArrayList<LessonData> data = null;
    
    public LessonArrayAdapter(Context context, int layoutResourceId, ArrayList<LessonData> data) {
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
            holder.lessonTitle = (TextView)row.findViewById(R.id.txtLessonTitle);
            holder.lessonImgIcon = (ImageView)row.findViewById(R.id.imgLessonIcon);
            holder.lessonAssignments = (TextView)row.findViewById(R.id.txtLessonAssignments);
            holder.lessonID = (TextView)row.findViewById(R.id.txtLessonId);
            
            row.setTag(holder);
        }
        else
        {
            holder = (LessonDataHolder)row.getTag();
        }
        
        LessonData lesson = data.get(position);
        holder.lessonTitle.setText(lesson.getLessonTitle());
        holder.lessonImgIcon.setImageResource(lesson.getAssignedIcon());
        holder.lessonAssignments.setText(lesson.getLessonAssignmentsString());
        holder.lessonID.setText(lesson.getLessonId());
        
        return row;
    }
    
    static class LessonDataHolder
    {
    	TextView lessonTitle;
    	ImageView lessonImgIcon;
        TextView lessonAssignments;
        TextView lessonID;
    }
}
