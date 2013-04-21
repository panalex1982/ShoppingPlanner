package com.bue.shoppingplanner;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;

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
		try{
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
				//Insert Shop Description
				ShopDescription desc=new ShopDescription();
				desc.setName("Unknown");
				db.addShopDescription(desc);//1
				desc.setName("Local Store");
				db.addShopDescription(desc);//2
				desc.setName("Super Market");
				db.addShopDescription(desc);//3
				desc.setName("Specialized Store");
				db.addShopDescription(desc);//4
				desc.setName("Local Store");
				db.addShopDescription(desc);//5
				desc.setName("Official Store");
				db.addShopDescription(desc);//6
				desc.setName("Other");
				db.addShopDescription(desc);//7
				//Insert Address
				Address address=new Address();
				address.setStreetName("not specified");
				address.setNumber("0");
				address.setCity("not specified");
				address.setArea("not specified");
				address.setZip("not specified");
				address.setCountry("not specified");
				db.addAddress(address);//1
				Shop shop=new Shop();
				shop.setName("not specified");
				shop.setAddress(1);
				shop.setShopDescription(1);
				db.addShop(shop);//1
			}
		}catch(Exception ex){
			Log.d("Initialize Exception", ex.toString());
		}
		
	}
	
	

}
