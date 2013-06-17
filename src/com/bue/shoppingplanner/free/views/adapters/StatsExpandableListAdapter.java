package com.bue.shoppingplanner.free.views.adapters;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.CurrencyController;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class StatsExpandableListAdapter extends BaseExpandableListAdapter {
	
	protected Context context;
	protected int layoutResourceId;
	protected int layoutChildResourceId;
	protected List<String[]> groupData;
	protected List<ArrayList<String[]>> childData;
	

	public StatsExpandableListAdapter(Context context, int textViewResourceId, int layoutChildResourceId,
			List<String[]> data, List<ArrayList<String[]>> childData) {
		super();
		this.context=context;
		this.layoutResourceId=textViewResourceId;
		this.layoutChildResourceId=layoutChildResourceId;
		this.groupData=data;
		this.childData=childData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		List tmp=childData.get(groupPosition);
		return tmp.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
        {
			LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutChildResourceId, parent, false);
        }
            TextView group=(TextView) convertView.findViewById(R.id.nameStatsElementChildTextView);
            TextView amount=(TextView) convertView.findViewById(R.id.amountStatsElementChildTextView);
            TextView currency=(TextView) convertView.findViewById(R.id.currencyStatsElementChildTextView);
           
    		String[] rowData=(String[]) getChild(groupPosition, childPosition);
    		group.setText(rowData[0]);
    		amount.setText(rowData[1]);
    		currency.setText(getCurrencySymbol());
        
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupData.size();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		//View row=convertView;
		//StatsElementHolder holder=null;
		if(convertView == null)
        {
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
           // holder =new StatsElementHolder();
            
            
            //row.setTag(holder);
//        }else{
//        	holder=(StatsElementHolder)row.getTag();
//        }
        }
		
        TextView group=(TextView) convertView.findViewById(R.id.nameStatsElementTextView);
        TextView amount=(TextView) convertView.findViewById(R.id.amountStatsElementTextView);
        TextView currency=(TextView) convertView.findViewById(R.id.currencyStatsElementTextView);
        
        
		String[] rowData=(String[]) getGroup(groupPosition);
		group.setText(rowData[0]);
		amount.setText(rowData[1]);
		currency.setText(getCurrencySymbol());
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getCurrencySymbol(){
		CurrencyController cController=new CurrencyController(context);
        String currencyIso=cController.getDefaultCurrency();
        Currency currency=Currency.getInstance(currencyIso);
        return currency.getSymbol();
	}

}
