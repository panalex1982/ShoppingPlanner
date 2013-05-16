package com.bue.shoppingplanner.views.adapters;

import java.util.ArrayList;
import java.util.List;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.helpers.ShoppingListElementHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

public class ShoppingListElementArrayAdapter extends ArrayAdapter<ShoppingListElementHelper> {
	
	 protected Context context;
	 protected int layoutResourceId;   
	 protected List<ShoppingListElementHelper> data = null;
	 protected boolean listItemChanged;

	public ShoppingListElementArrayAdapter(Context context,	int textViewResourceId, List<ShoppingListElementHelper> data) {
		super(context, textViewResourceId, data);
		this.context=context;
		this.layoutResourceId=textViewResourceId;
		this.data=data;
		this.listItemChanged=false;
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
		ImageView listElementAddImageView;
        ImageView listElementRemoveImageView;
        //holder.listElementEditImageView= (ImageView)row.findViewById(R.id.listElementEditImageView);
        TextView listElementTextView;
        CheckBox listElementCheckBox;
        final TableRow hiddingTableRow;
        final TableLayout elementTableLayout;
       
        if(row == null)
        {
            LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            //holder = new ShoppingListElementHolder();
           
           
            //row.setTag(holder);
        }
//        else
//        {
//            holder = (ShoppingListElementHolder)row.getTag();
//        }
        
        listElementAddImageView = (ImageView)row.findViewById(R.id.listElementAddImageView);
        listElementRemoveImageView = (ImageView)row.findViewById(R.id.listElementRemoveImageView);
        //holder.listElementEditImageView= (ImageView)row.findViewById(R.id.listElementEditImageView);
        listElementTextView = (TextView)row.findViewById(R.id.listElementTextView);
        listElementCheckBox = (CheckBox)row.findViewById(R.id.listElementCheckBox);
        
        //Hidding Menu
        //LinearLayout hiddingLayout=new LinearLayout(context);
        EditText hideText=new EditText(context);
        Button hideOkButton=new Button(context);
        hideOkButton.setText("OK");
        Button hideCancelButton=new Button(context);
        
        hiddingTableRow=new TableRow(context);
        hiddingTableRow.addView(hideText);
        hiddingTableRow.addView(hideOkButton);
        hiddingTableRow.addView(hideCancelButton);
        
        elementTableLayout=(TableLayout)row.findViewById(R.id.elementTableLayout);
        
        elementTableLayout.removeView(hiddingTableRow);
        final ShoppingListElementHelper listElement = data.get(position);
        listElementTextView.setText(listElement.getProduct()+" "+listElement.getPrice()+" x"+listElement.getQuantity());
        listElementCheckBox.setChecked(listElement.isChecked());
        
        listElementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){        	
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				try{
					/** Getting the view position in the ListView */
		           // ListView parent = (ListView)(buttonView.getParent()).getParent();
		            int pos =getPosition(listElement);// parent.getPositionForView(buttonView);
		            
					listElement.setChecked(isChecked);
					data.remove(pos);
					data.add(pos,listElement);
					listItemChanged=true;
				}catch(Exception ex){
					Log.d("onCheckedChangedError", ex.toString());
				}
			}
        	
        });
        listElementAddImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/** Getting the view position in the ListView */
	            //ListView parent = (ListView)(v.getParent()).getParent();
	            int pos = getPosition(listElement);//parent.getPositionForView(v);
	            
				listElement.setQuantity(listElement.getQuantity()+1);
				data.remove(pos);
				data.add(pos,listElement);
				listItemChanged=true;
				notifyDataSetChanged();
			}
		});
       listElementRemoveImageView.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			/** Getting the view position in the ListView */
            //ListView parent = (ListView)(((TableLayout)((v.getParent()).getParent())).getParent());
            int pos = getPosition(listElement);//parent.getPositionForView(v);
            
            int newQuantity=listElement.getQuantity()-1;
            if(newQuantity<1)
            	newQuantity=1;
			listElement.setQuantity(newQuantity);
			data.remove(pos);
			data.add(pos,listElement);
			listItemChanged=true;
			notifyDataSetChanged();
		}
       });
       
        listElementTextView.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_UP){
				elementTableLayout.addView(hiddingTableRow);
				Log.d("Touch","pos"+getPosition(listElement));
			}
			return true;
		}
	});
        
        hideOkButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				elementTableLayout.removeView(hiddingTableRow);				
			}
		});
       
        return row;
    }
	
	
   
//    @Override
//	public void add(ShoppingListElementHelper element) {
//		super.add(element);
//		data.add(element);
//	}
    
    
    
//    @Override
//	public int getPosition(ShoppingListElementHelper item) {
//		return super.getPosition(item);
//	}

	public double getTotalCost(){
    	double total=0.0;
    	for(ShoppingListElementHelper element:data){
    		total=total+element.getPrice()*element.getQuantity();
    	}
    	return total;
    }

    public boolean isListItemChanged() {
		return listItemChanged;
	}
    
    


	private class ShoppingListElementHolder
    {
        ImageView listElementAddImageView, listElementRemoveImageView;//, listElementEditImageView;
        TextView listElementTextView;
        CheckBox listElementCheckBox;
        TableRow hiddingTableRow;
        TableLayout elementTableLayout;
    }
	
	

}
