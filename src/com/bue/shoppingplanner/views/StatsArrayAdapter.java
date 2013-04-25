package com.bue.shoppingplanner.views;

import java.util.List;

import com.bue.shoppingplanner.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class StatsArrayAdapter extends ArrayAdapter<String[]> {
	
	protected Context context;
	protected int layoutResourceId;
	protected List<String[]> data;
	

	public StatsArrayAdapter(Context context, int textViewResourceId,
			List<String[]> data) {
		super(context, textViewResourceId, data);
		this.context=context;
		this.layoutResourceId=textViewResourceId;
		this.data=data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;
		if(row == null)
        {
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            row.setTag(layoutResourceId, data);
        }else{
        	
        }
		return row;
	}
	
	

}
