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

public class InspirationArrayAdapterMultiple extends ArrayAdapter<InspirationData>{

	Context context; 
    int layoutResourceId;    
    ArrayList<InspirationData> data = null;
    
    public InspirationArrayAdapterMultiple(Context context, int layoutResourceId, ArrayList<InspirationData> data) {
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
            holder.inspirationID = (TextView)row.findViewById(R.id.txtInspirationId);
            holder.inspirationContent = (CheckedTextView)row.findViewById(R.id.txtInspirationContent);

            row.setTag(holder);
        }
        else
        {
            holder = (InspirationDataHolder)row.getTag();
        }
        
        InspirationData inspiration = data.get(position);
        
        holder.inspirationID.setText(inspiration.getInspirationId());
        holder.inspirationContent.setText(inspiration.getInspirationContent());     
        
        return row;
    }
    
    static class InspirationDataHolder
    {
    	TextView inspirationID;
    	CheckedTextView inspirationContent;        
    }
}
