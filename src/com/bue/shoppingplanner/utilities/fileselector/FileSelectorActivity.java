package com.bue.shoppingplanner.utilities.fileselector;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.utilities.SerializeObject;
import com.bue.shoppingplanner.views.SettingsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FileSelectorActivity extends FragmentActivity {

	private Button mLoadButton;

	private Button mSaveButton;

	/** Sample filters array */
	final String[] mFileFilter = { "*.*", ".jpeg", ".txt", ".png" };

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_selector);

		mLoadButton = (Button) findViewById(R.id.button_load);
		mSaveButton = (Button) findViewById(R.id.button_save);

		mLoadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				new FileSelector(FileSelectorActivity.this, FileOperation.LOAD, mLoadFileListener, mFileFilter).show();
			}
		});

		mSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				new FileSelector(FileSelectorActivity.this, FileOperation.SAVE, mSaveFileListener, mFileFilter).show();
			}
		});
	}

	OnHandleFileListener mLoadFileListener = new OnHandleFileListener() {
		@Override
		public void handleFile(final String filePath) {
			SerializeObject.importDB(filePath, getBaseContext());
			Toast.makeText(FileSelectorActivity.this, "Load: " + filePath, Toast.LENGTH_SHORT).show();
		}
	};

	OnHandleFileListener mSaveFileListener = new OnHandleFileListener() {
		@Override
		public void handleFile(final String filePath) {//TODO: create file
			SerializeObject.exportDB(filePath, getBaseContext());
			Toast.makeText(FileSelectorActivity.this, "Save: " + filePath, Toast.LENGTH_SHORT).show();
		}
	};

}