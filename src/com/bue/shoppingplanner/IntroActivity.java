package com.bue.shoppingplanner;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.model.Address;
import com.bue.shoppingplanner.model.CommercialProduct;
import com.bue.shoppingplanner.model.DatabaseHandler;
import com.bue.shoppingplanner.model.ProductGroup;
import com.bue.shoppingplanner.model.ProductKind;
import com.bue.shoppingplanner.model.Shop;
import com.bue.shoppingplanner.model.ShopDescription;
import com.bue.shoppingplanner.model.UnknownBarcode;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
		
			if(ProductKind.getProductKindCount(db)<=0){
				//Insert Groups
				ProductGroup group=new ProductGroup();
				group.setName("Main Need");//1
				group.addProductGroup(db);
				group.setName("Secondary Need");//2
				group.addProductGroup(db);
				group.setName("Other");//3
				group.addProductGroup(db);
				group.setName("Bill");//4
				group.addProductGroup(db);
				group.setName("Tax");//5
				group.addProductGroup(db);
				group.setName("Entertainment");//6
				group.addProductGroup(db);
				group.setName("Maintenance");//7
				group.addProductGroup(db);
				
				//Insert Kinds
				ProductKind kind=new ProductKind();
				kind.setName("Food");//1		
				kind.addProductKind(db);
				kind.setName("Drinks");//2		
				kind.addProductKind(db);
				kind.setName("Health");//3		
				kind.addProductKind(db);
				kind.setName("Telecomunication");//4		
				kind.addProductKind(db);
				kind.setName("Sports");//5		
				kind.addProductKind(db);
				kind.setName("Hobbies");//6		
				kind.addProductKind(db);
				kind.setName("Technology");//7		
				kind.addProductKind(db);
				kind.setName("Work Equipment");//8		
				kind.addProductKind(db);
				kind.setName("Games");//9		
				kind.addProductKind(db);
				kind.setName("Home Equipment");//10		
				kind.addProductKind(db);
				kind.setName("Travel");//11		
				kind.addProductKind(db);
				kind.setName("Houshold");//12		
				kind.addProductKind(db);
				kind.setName("Beauty/Personal Care");//13		
				kind.addProductKind(db);
				kind.setName("Children Products");//14		
				kind.addProductKind(db);
				kind.setName("Mobile Phone Bills");//15		
				kind.addProductKind(db);
				kind.setName("Phone Bills");//16		
				kind.addProductKind(db);
				kind.setName("Energy Bills");//17		
				kind.addProductKind(db);
				kind.setName("Electronics");//18		
				kind.addProductKind(db);
				kind.setName("Cigarettes");//19		
				kind.addProductKind(db);
				kind.setName("Vehicles & Parts");//20		
				kind.addProductKind(db);
				kind.setName("Clothes");//21		
				kind.addProductKind(db);
				kind.setName("Heyngine");//22		
				kind.addProductKind(db);
				kind.setName("Pet");//23		
				kind.addProductKind(db);
				kind.setName("Home Entertainment");//24		
				kind.addProductKind(db);
				kind.setName("Outside Entertainment");//25		
				kind.addProductKind(db);
				kind.setName("Other");//26		
				kind.addProductKind(db);
				//Insert Unknown/No Specified barcode
				CommercialProduct cp=new CommercialProduct("-1","Unknown", "Unknown");
				cp.addCommercialProduct(db);
				UnknownBarcode ub=new UnknownBarcode(-1);
				ub.addUnknownBarcode(db);				
				//Insert Shop Description
				ShopDescription desc=new ShopDescription();
				desc.setName("Unknown");
				desc.addShopDescription(db);//1
				desc.setName("Local Store");
				desc.addShopDescription(db);//2
				desc.setName("Super Market");
				desc.addShopDescription(db);//3
				desc.setName("Specialized Store");
				desc.addShopDescription(db);//4
				desc.setName("Mini Market");
				desc.addShopDescription(db);//5
				desc.setName("Official Store");
				desc.addShopDescription(db);//6
				desc.setName("Other");
				desc.addShopDescription(db);//7
				//Insert Address
				Address address=new Address();
				address.setStreetName("not specified");
				address.setNumber("0");
				address.setCity("not specified");
				address.setArea("not specified");
				address.setZip("not specified");
				address.setCountry("not specified");
				address.addAddress(db);//1
				Shop shop=new Shop();
				shop.setName("not specified");
				shop.setAddress(1);
				shop.setShopDescription(1);
				shop.addShop(db);//1				
			}
		}catch(Exception ex){
			Log.d("Initialize Exception", ex.toString());
		}
		
	}
	
	

}
