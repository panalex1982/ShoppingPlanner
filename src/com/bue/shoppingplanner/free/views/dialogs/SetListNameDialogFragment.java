package com.bue.shoppingplanner.free.views.dialogs;

import com.bue.shoppingplanner.free.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SetListNameDialogFragment extends DialogFragment {
	
	/** The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface SetListNameDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	private SetListNameDialogListener mListener;
	private EditText setListNameEditText;
	
	private String listName;
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            // Instantiate the AddProductDialogListener so we can send events to the host
            mListener = (SetListNameDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
		throw new ClassCastException(activity.toString()
				+ " must implement SetListNameDialogListener");
        }
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView=inflater.inflate(R.layout.dialog_fragment_set_list_name, null);
		
		Bundle listNameBundle=getArguments();
		if(listNameBundle!=null){
			listName=(listNameBundle.getString("shoppingListName")=="-1"?"":listNameBundle.getString("shoppingListName"));
		}
		
		//set name EditText
		setListNameEditText=(EditText) dialogMainView.findViewById(R.id.setListNameEditText);
		setListNameEditText.setText(listName);
		
		builder.setTitle("Set List Name")
		.setView(dialogMainView)
		.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	listName=setListNameEditText.getText().toString();
                mListener.onDialogPositiveClick(SetListNameDialogFragment.this);
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	//Send the negative button event back
            	mListener.onDialogNegativeClick(SetListNameDialogFragment.this);
            }
        });
		return builder.create();
	}
	
	
	
	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

}
