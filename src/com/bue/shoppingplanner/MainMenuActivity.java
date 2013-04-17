package com.bue.shoppingplanner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.R.layout;
import com.bue.shoppingplanner.R.menu;
import com.bue.shoppingplanner.model.*;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		// Show the Up button in the action bar.
		setupActionBar();

        /*DatabaseHandler db = new DatabaseHandler(this);
        
        *//**
* CRUD Operations
* *//*
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
         db.addProductKind(new ProductKind("Food",0));
        db.addProductKind(new ProductKind("Drink",0));
        db.addProductKind(new ProductKind("Clothes",0));
        db.addProductKind(new ProductKind("Computer Hardware",0));
        db.addCommercialProduct(new CommercialProduct("-1","Unknown", "Unknown"));
        db.addCommercialProduct(new CommercialProduct("5201399011201", "Φασόλια Μέτρια 3Α", "3 άλφα"));
        db.addProduct(new Product("Μέτρια Φασόλια 3Α", "5201399011201", 1));
        db.addShopDescription(new ShopDescription("Super Market"));
        db.addShopDescription(new ShopDescription("Mini Market"));
        db.addShopDescription(new ShopDescription("Local Store"));
        db.addShopDescription(new ShopDescription("Specialized Shop"));
        db.addAddress(new Address("Βόλου", "23", "not specified", "Αλμυρός",
    			"Μαγνησίας", "Greece"));
        db.addAddress(new Address("Αλμυρά", "12", "not specified", "Νέα Αγχίαλος",
    			"Μαγνησίας", "Greece"));
        db.addShop(new Shop(1, 2, "Γαλαξίας"));
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        db.addBuys(new Buys(-1, 2, 1, 1.49, 2, format));
        
        
 
        // Reading all contacts
               
     // Reading all product kinds
        Log.d("Reading: ", "Reading all product kinds..");
        List<ProductKind> productKinds = db.getAllProductKind();
 
        for (ProductKind pkind : productKinds) {
            String log2 = "Id: "+pkind.getId()+" ,Name: " + pkind.getName() + " ,Group Id: " + pkind.getGroup_id();
                // Writing Contacts to log
        Log.d("Name: ", log2);
        }
     // Reading all CommercialProduct
        Log.d("Reading: ", "Reading all product CommercialProduct..");
        List<CommercialProduct> commercialProduct = db.getAllCommercialProduct();
 
        for (CommercialProduct cmp : commercialProduct) {
            String log3 = "Barcode: "+cmp.getBarcode()+" ,Name: " + cmp.getCommercialName() + " ,Brand: " + cmp.getCompanyBrand();
                // Writing Contacts to log
        Log.d("Name: ", log3);
        }
     // Reading all product
        Log.d("Reading: ", "Reading all product ..");
        List<Product> product = db.getAllProduct();
        for (Product pk : product) {
        	ProductKind kind=db.getProductKind(pk.getKind());
            String log4 = "Id: "+pk.getId()+" ,Name: " + pk.getName() + " ,Kind: " + kind.getName() + "Barcode"+pk.getBarcode();
                // Writing Contacts to log
        Log.d("Name: ", log4);
        }
        
     // Reading all Shop Desc
        Log.d("Reading: ", "Reading all Shop Desc..");
        List<ShopDescription> shopDescription = db.getAllShopDescription();
        for (ShopDescription sdesc : shopDescription) {
            String log5 = "Id: "+sdesc.getId()+" ,Name: " + sdesc.getName();
                // Writing Contacts to log
        Log.d("Name: ", log5);
        }
        
     // Reading all address
        Log.d("Reading: ", "Reading all product address..");
        List<Address> address = db.getAllAddress();
        for (Address add : address) {
            String log6 = "Id: "+add.getId()+" ,Street Name: " + add.getStreetName() + " ,"+add.getNumber() + ", "+add.getCity()+", "+add.getCounty()+", "+add.getCountry();
                // Writing Contacts to log
        Log.d("Name: ", log6);
        }
        
     // Reading all Buys
        Log.d("Reading: ", "Reading all product buys..");
        List<Buys> buys = db.getAllBuys();
        for (Buys buy : buys) {
        	Product prod=db.getProduct(buy.getProduct());
        	Shop sp=db.getShop(buy.getShop());
        	Address sadd=db.getAddress(sp.getAddress());
            String log7 = "Id: "+buy.getId()+" ,Product Name: " + prod.getName() + ", Shop name: "+sp.getName() + ", @ "+sadd.getStreetName()+", "+sadd.getNumber()+", "+sadd.getCity()+", "+sadd.getCounty()+", "+sadd.getCountry()+"\nTotal Price: "+buy.getUnit_price()*buy.getAmount()+" Euro, Purchace date: "+buy.getDate();
                // Writing Contacts to log
        Log.d("Name: ", log7);
        }*/
        
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
