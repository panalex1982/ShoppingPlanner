package com.bue.shoppingplanner.views.adapters;

import java.util.List;

import com.bue.shoppingplanner.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
StatsElementHolder holder=null;
if(row == null)
        {
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder =new StatsElementHolder();
            
            holder.group=(TextView) row.findViewById(R.id.nameStatsElementTextView);
            holder.amount=(TextView) row.findViewById(R.id.amountStatsElementTextView);
            holder.currency=(TextView) row.findViewById(R.id.currencyStatsElementTextView);
            row.setTag(holder);
        }else{
         holder=(StatsElementHolder)row.getTag();
        }
String[] rowData=data.get(position);
holder.group.setText(rowData[0]);
holder.amount.setText(rowData[1]);
holder.currency.setText(rowData[2]);

return row;
}

private class StatsElementHolder{
TextView group, amount, currency;
}



}