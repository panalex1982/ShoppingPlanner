package com.bue.shoppingplanner.free.views.dialogs;

import com.bue.shoppingplanner.free.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

public class AboutDialogFragment extends DialogFragment {
	private WebView aboutWebView;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create main dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogMainView = inflater.inflate(R.layout.dialog_about,
				null);
		aboutWebView=(WebView) dialogMainView.findViewById(R.id.aboutWebView);
		builder.setTitle(getResources().getString(R.string.about))
			.setView(dialogMainView)
			.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
				});
		return builder.create();
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		try{
			aboutWebView.loadUrl("file:///android_asset/about.html");
		}catch(Exception ex){
			Log.d("Web view: ",ex.toString());
		}
	}
	
	

}
