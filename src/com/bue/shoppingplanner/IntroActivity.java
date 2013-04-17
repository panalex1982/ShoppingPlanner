package com.bue.shoppingplanner;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

public class IntroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intro, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_UP){
			openMainApplication();
		}
		return true;
	}
	
	/**
	 * Opens the Main Menu Activity.
	 */
	private void openMainApplication(){;
		try{
			startActivity(new Intent(IntroActivity.this, MainMenuActivity.class));
		}catch(Exception ex){
			String exs=ex.toString();
			Log.d("Exception", exs);
		}	
	}
	
	/**
	 * Adds default records to the database.
	 */
	
	public void initializeDatabase(){
		DatabaseHandler db=new DatabaseHandler(this);
		
		if(db.getProductKindCount()>0){
			ProductGroup group=new ProductGroup();
			group.setName("Main Need");//1
			db.addProductGroup(group);
			group.setName("Secondary Need");//2
			db.addProductGroup(group);
			group.setName("Bill");//3
			db.addProductGroup(group);
			group.setName("Tax");//4
			db.addProductGroup(group);
			group.setName("Entertainment");//4
			db.addProductGroup(group);
			
		}
		
	}
	
	

}
