package com.bue.shoppingplanner.views.dialogs;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class StatsFilterDialogFragment extends DialogFragment {
	
	/** The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface StatsFilterDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    
    /**The selected items from the filterListView*/
    private ArrayList<String> filtersList;
	
    /**The List view of the available filters*/
	private ListView filterListView;
	
	/**The listener to communicate with the activity*/
	private StatsFilterDialogListener mListener;
	
	/**Where do the filter applied*/
	private String filterType;
	
	/**Fragment Id of the fragment which called the fragment dialog*/
	private int fragmentId;
    
    @Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            // Instantiate the StatsFilterDialogListener so we can send events to the host
            mListener = (StatsFilterDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
		throw new ClassCastException(activity.toString()
				+ " must implement StatsFilterDialogListener");
        }
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.dialog_stats_filter, null);
		
		Bundle filterBundle=getArguments();
		if(filterBundle!=null){
			filterType=filterBundle.getString("filterType");
			fragmentId=filterBundle.getInt("fragmentId");
			Log.d("Open dialog", filterType+" "+fragmentId);
		}
		
		filterListView=(ListView) dialogMainView.findViewById(R.id.filterListView);
		
		builder.setTitle("Set List Name")
		.setView(dialogMainView)
		.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	//TODO:Get selected items from filterListView
                mListener.onDialogPositiveClick(StatsFilterDialogFragment.this);
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	//Send the negative button event back
            	mListener.onDialogNegativeClick(StatsFilterDialogFragment.this);
            }
        });
		return builder.create();
	}

	public ListView getFilterListView() {
		return filterListView;
	}

	public int getFragmentId() {
		return fragmentId;
	}
	
	

}
