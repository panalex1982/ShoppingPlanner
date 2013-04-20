package com.bue.shoppingplanner.views;

import java.util.ArrayList;
import java.util.List;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

public class ShoppingListElementArrayAdapter extends ArrayAdapter<ShoppingListElementHelper> {
	
	 protected Context context;
	 protected int layoutResourceId;   
	 protected List<ShoppingListElementHelper> data = null;

	public ShoppingListElementArrayAdapter(Context context,	int textViewResourceId, List<ShoppingListElementHelper> data) {
		super(context, textViewResourceId, data);
		this.context=context;
		this.layoutResourceId=textViewResourceId;
		this.data=data;
	}
	
	/*public ShoppingListElementArrayAdapter(Context context,	int textViewResourceId) {
		super(context, textViewResourceId);
		this.context=context;
		this.layoutResourceId=textViewResourceId;
		data=new ArrayList<ShoppingListElementHelper>();
	}*/

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;
		ShoppingListElementHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new ShoppingListElementHolder();
            holder.listElementAddImageView = (ImageView)row.findViewById(R.id.listElementAddImageView);
            holder.listElementRemoveImageView = (ImageView)row.findViewById(R.id.listElementRemoveImageView);
            holder.listElementEditImageView= (ImageView)row.findViewById(R.id.listElementEditImageView);
            holder.listElementTextView = (TextView)row.findViewById(R.id.listElementTextView);
            holder.listElementCheckBox = (CheckBox)row.findViewById(R.id.listElementCheckBox);
           
            row.setTag(holder);
        }
        else
        {
            holder = (ShoppingListElementHolder)row.getTag();
        }
       
        final ShoppingListElementHelper listElement = data.get(position);
        holder.listElementTextView.setText(listElement.getProduct()+" "+listElement.getPrice()+" x"+listElement.getQuantity());
        holder.listElementCheckBox.setChecked(listElement.isChecked());
        
        holder.listElementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){        	
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				try{
					/** Getting the view position in the ListView */
		            ListView parent = (ListView)(buttonView.getParent()).getParent();
		            int pos = parent.getPositionForView(buttonView);
		            
					listElement.setChecked(isChecked);
					data.remove(pos);
					data.add(pos,listElement);
				}catch(Exception ex){
					Log.d("onCheckedChangedError", ex.toString());
				}
			}
        	
        });
       
        return row;
    }
	
	
   
    @Override
	public void add(ShoppingListElementHelper element) {
		super.add(element);
		data.add(element);
	}



	private class ShoppingListElementHolder
    {
        ImageView listElementAddImageView, listElementRemoveImageView, listElementEditImageView;
        TextView listElementTextView;
        CheckBox listElementCheckBox;
        
    }

}
