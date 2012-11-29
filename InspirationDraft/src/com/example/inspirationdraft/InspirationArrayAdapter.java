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

public class InspirationArrayAdapter extends ArrayAdapter<InspirationData>{

	Context context; 
    int layoutResourceId;    
    ArrayList<InspirationData> data = null;
    
    public InspirationArrayAdapter(Context context, int layoutResourceId, ArrayList<InspirationData> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        InspirationDataHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new InspirationDataHolder();
            holder.inspirationContent = (TextView)row.findViewById(R.id.txtInspirationContent);
            holder.inspirationImgIcon = (ImageView)row.findViewById(R.id.imgInspirationIcon);
            holder.inspirationAssignments = (TextView)row.findViewById(R.id.txtInspirationAssignments);
            holder.inspirationID = (TextView)row.findViewById(R.id.txtInspirationID);
            
            row.setTag(holder);
        }
        else
        {
            holder = (InspirationDataHolder)row.getTag();
        }
        
        InspirationData inspiration = data.get(position);
        holder.inspirationContent.setText(inspiration.getContent());
        holder.inspirationImgIcon.setImageResource(inspiration.getAssignedIcon());
        holder.inspirationAssignments.setText(inspiration.getLessonAssignmentsString());
        holder.inspirationID.setText(inspiration.getID());
        
        return row;
    }
    
    static class InspirationDataHolder
    {
    	TextView inspirationContent;
    	ImageView inspirationImgIcon;
        TextView inspirationAssignments;
        TextView inspirationID;
    }
}
