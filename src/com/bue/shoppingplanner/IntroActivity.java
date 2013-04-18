package com.bue.shoppingplanner;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;

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
		initializeDatabase();
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
		
		if(db.getProductKindCount()<=0){
			//Insert Groups
			ProductGroup group=new ProductGroup();
			group.setName("Main Need");//1
			db.addProductGroup(group);
			group.setName("Secondary Need");//2
			db.addProductGroup(group);
			group.setName("Other");//3
			db.addProductGroup(group);
			group.setName("Bill");//4
			db.addProductGroup(group);
			group.setName("Tax");//5
			db.addProductGroup(group);
			group.setName("Entertainment");//6
			db.addProductGroup(group);
			group.setName("Maintenance");//7
			db.addProductGroup(group);
			
			//Insert Kinds
			ProductKind kind=new ProductKind();
			kind.setName("Food");//1		
			db.addProductKind(kind);
			kind.setName("Drinks");//2		
			db.addProductKind(kind);
			kind.setName("Health");//3		
			db.addProductKind(kind);
			kind.setName("Telecomunication");//4		
			db.addProductKind(kind);
			kind.setName("Sports");//5		
			db.addProductKind(kind);
			kind.setName("Hobbies");//6		
			db.addProductKind(kind);
			kind.setName("Technology");//7		
			db.addProductKind(kind);
			kind.setName("Work Equipment");//8		
			db.addProductKind(kind);
			kind.setName("Games");//9		
			db.addProductKind(kind);
			kind.setName("Home Equipment");//10		
			db.addProductKind(kind);
			kind.setName("Travel");//11		
			db.addProductKind(kind);
			kind.setName("Houshold");//12		
			db.addProductKind(kind);
			kind.setName("Beauty/Personal Care");//13		
			db.addProductKind(kind);
			kind.setName("Children Products");//14		
			db.addProductKind(kind);
			kind.setName("Mobile Phone Bills");//15		
			db.addProductKind(kind);
			kind.setName("Phone Bills");//16		
			db.addProductKind(kind);
			kind.setName("Energy Bills");//17		
			db.addProductKind(kind);
			kind.setName("Electronics");//18		
			db.addProductKind(kind);
			kind.setName("Cigarettes");//19		
			db.addProductKind(kind);
			kind.setName("Vehicles & Parts");//20		
			db.addProductKind(kind);
			kind.setName("Clothes");//21		
			db.addProductKind(kind);
			kind.setName("Heyngine");//22		
			db.addProductKind(kind);
			kind.setName("Pet");//23		
			db.addProductKind(kind);
			kind.setName("Home Entertainment");//24		
			db.addProductKind(kind);
			kind.setName("Outside Entertainment");//25		
			db.addProductKind(kind);
			kind.setName("Other");//26		
			db.addProductKind(kind);
			
		}
		
	}
	
	

}
