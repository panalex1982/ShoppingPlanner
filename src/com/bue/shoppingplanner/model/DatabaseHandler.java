package com.bue.shoppingplanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "shoppingPlannerDB";
    
    //Tables name
    private static final String TABLE_PRODUCT_GROUP="productGroup";
    private static final String TABLE_PRODUCT_KIND="productKind";
    private static final String TABLE_PRODUCT="product";
    private static final String TABLE_COMMERCIAL_PRODUCT="commercialProduct";
    private static final String TABLE_SHOP="shop";
    private static final String TABLE_SHOP_DESCRIPTION="shopDescription";
    private static final String TABLE_ADDRESS="address";
    private static final String TABLE_BUYS="buys";
    
    //Column Names
    //Product Group
    private static final String PRODUCT_GROUP_ID="id";
	private static final String PRODUCT_GROUP_NAME="name";
    
    //Product Kind Table
    private static final String PRODUCT_KIND_ID="id";
	private static final String PRODUCT_KIND_NAME="name";
	//private static final String PRODUCT_KIND_GROUP_ID="groupId";
	
	//Product Table
	private static final String PRODUCT_ID="id";
	private static final String PRODUCT_NAME="name";
	private static final String PRODUCT_BARCODE="barcode";
	private static final String PRODUCT_KIND="kind";
	private static final String PRODUCT_GROUP_ID_FK="productGroupId";
	
	//Commercial Product
	private static final String COMMERCIAL_PRODUCT_BARCODE="barcode";
	private static final String COMMERCIAL_PRODUCT_NAME="commercialName";
	private static final String COMMERCIAL_PRODUCT_COMPANY_BRAND="companyBrand";
	
	//Shop
	private static final String SHOP_ID="id";
	private static final String SHOP_DESCRIPTION="shopDescription";
	private static final String SHOP_ADDRESS="address";
	private static final String SHOP_NAME="name";
	
	//Shop Description
	private static final String SHOP_DESCRIPTION_ID="id";
	private static final String SHOP_DESCRIPTION_NAME="name";
	
	//Address
	private static final String ADDRESS_ID="id";
	private static final String ADDRESS_STREET_NAME="streetName";
	private static final String ADDRESS_NUMBER="number";
	private static final String ADDRESS_AREA="area";
	private static final String ADDRESS_CITY="city";
	private static final String ADDRESS_COUNTY="county";
	private static final String ADDRESS_COUNTRY="country";
	
	//Buys
	private static final String BUYS_ID="id";
	private static final String BUYS_PRODUCT="product"; 
	private static final String BUYS_SHOP="shop";
	//user;//User not used in first version
	private static final String BUYS_UNIT_PRICE="unitPrice";
	private static final String BUYS_AMOUNT="amount";
	private static final String BUYS_DATE="date";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//Product Group
        String CREATE_TABLE_PRODUCT_GROUP="CREATE TABLE " +TABLE_PRODUCT_GROUP +"("
    	+ PRODUCT_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," 
        + PRODUCT_GROUP_NAME + " TEXT NOT NULL)";
    	
    	//Product Kind Table
       String CREATE_TABLE_PRODUCT_KIND="CREATE TABLE " + TABLE_PRODUCT_KIND + "("
                + PRODUCT_KIND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," 
                + PRODUCT_KIND_NAME + " TEXT NOT NULL)";
                //+ PRODUCT_KIND_GROUP_ID + " INTEGER)"; 
                //+ "FOREIGN KEY("+PRODUCT_KIND_GROUP_ID+") REFERENCES "+ TABLE_PRODUCT_GROUP+"("+PRODUCT_GROUP_ID+")";
        
        
        //Commercial Product
    	String CREATE_TABLE_COMMERCIAL_PRODUCT="CREATE TABLE " + TABLE_COMMERCIAL_PRODUCT+"("+
    											COMMERCIAL_PRODUCT_BARCODE+ " TEXT PRIMARY KEY,"+
    											COMMERCIAL_PRODUCT_NAME+ " TEXT NOT NULL,"+
    											COMMERCIAL_PRODUCT_COMPANY_BRAND+" TEXT)";
        
    	//Product Table
    	String CREATE_TABLE_PRODUCT="CREATE TABLE " + TABLE_PRODUCT +"("+
    								PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
    								PRODUCT_NAME + " TEXT NOT NULL," +
									PRODUCT_BARCODE + " TEXT NOT NULL," +
									PRODUCT_KIND+ " INTEGER NOT NULL," +
									PRODUCT_GROUP_ID_FK+ " INTEGER NOT NULL," +
									"FOREIGN KEY("+PRODUCT_BARCODE+") REFERENCES "+ TABLE_COMMERCIAL_PRODUCT+"("+COMMERCIAL_PRODUCT_BARCODE+"),"+
									"FOREIGN KEY("+PRODUCT_KIND+") REFERENCES "+ TABLE_PRODUCT_KIND+"("+PRODUCT_KIND_ID+")"+
									"FOREIGN KEY("+PRODUCT_GROUP_ID_FK+") REFERENCES "+ TABLE_PRODUCT_GROUP+"("+PRODUCT_GROUP_ID+")"+
									")";
    	
    	//Shop Description
    	String CREATE_TABLE_SHOP_DESCRIPTION="CREATE TABLE "+ TABLE_SHOP_DESCRIPTION +"("+
    							 SHOP_DESCRIPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
    							 SHOP_DESCRIPTION_NAME+ " TEXT NOT NULL)";
    	
    	//Address
    	String CREATE_TABLE_ADDRESS="CREATE TABLE "+ TABLE_ADDRESS +"("+
    							 ADDRESS_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ," +
    							 ADDRESS_STREET_NAME+ " TEXT NOT NULL," + 
    							 ADDRESS_NUMBER+ " TEXT,"+
    							 ADDRESS_AREA+ " TEXT,"+
    							 ADDRESS_CITY+ " TEXT,"+
    							 ADDRESS_COUNTY+ " TEXT,"+
    							 ADDRESS_COUNTRY+ " TEXT)";
    	
    	//Shop Table
    	String CREATE_TABLE_SHOP="CREATE TABLE "+ TABLE_SHOP +"("+
    							  SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
    							  SHOP_NAME+ " TEXT NOT NULL," + 
    							  SHOP_ADDRESS + " INTEGER NOT NULL," + 
    							  SHOP_DESCRIPTION+" INTEGER NOT NULL," +
    							  "FOREIGN KEY("+SHOP_ADDRESS+") REFERENCES "+ TABLE_ADDRESS+"("+ADDRESS_ID+"),"+
								  "FOREIGN KEY("+SHOP_DESCRIPTION+") REFERENCES "+ TABLE_SHOP_DESCRIPTION+"("+SHOP_DESCRIPTION_ID+")"+
								  ")";
    	
    	//Buys
    	String CREATE_TABLE_BUYS="CREATE TABLE "+ TABLE_BUYS +"("+
    							 BUYS_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ," +
    							 BUYS_PRODUCT + " INTEGER NOT NULL," +
    							 BUYS_SHOP + " INTEGER NOT NULL," +
    							 BUYS_UNIT_PRICE + " REAL NOT NULL," +
    							 BUYS_AMOUNT + " INTEGER NOT NULL," +
    							 BUYS_DATE + " TEXT NOT NULL," +
    							 "FOREIGN KEY("+BUYS_PRODUCT+") REFERENCES "+ TABLE_PRODUCT+"("+PRODUCT_ID+"),"+
								 "FOREIGN KEY("+BUYS_SHOP+") REFERENCES "+ TABLE_SHOP+"("+SHOP_ID+")"+
								 ")";
    	
    	
    	
    	//Create all tables
    	db.execSQL(CREATE_TABLE_PRODUCT_GROUP);
    	db.execSQL(CREATE_TABLE_PRODUCT_KIND);
    	db.execSQL(CREATE_TABLE_COMMERCIAL_PRODUCT);
    	db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_SHOP_DESCRIPTION);
		db.execSQL(CREATE_TABLE_ADDRESS);
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_BUYS);
		
		//Insert Groups
		ProductGroup group=new ProductGroup();
		group.setName("Main Need");//1
		addProductGroup(group);
		group.setName("Secondary Need");//2
		addProductGroup(group);
		group.setName("Other");//3
		addProductGroup(group);
		group.setName("Bill");//4
		addProductGroup(group);
		group.setName("Tax");//5
		addProductGroup(group);
		group.setName("Entertainment");//6
		addProductGroup(group);
		group.setName("Maintenance");//7
		addProductGroup(group);
		
		//Insert Kinds
		ProductKind kind=new ProductKind();
		kind.setName("Food");//1		
		addProductKind(kind);
		kind.setName("Drinks");//2		
		addProductKind(kind);
		kind.setName("Health");//3		
		addProductKind(kind);
		kind.setName("Telecomunication");//4		
		addProductKind(kind);
		kind.setName("Sports");//5		
		addProductKind(kind);
		kind.setName("Hobbies");//6		
		addProductKind(kind);
		kind.setName("Technology");//7		
		addProductKind(kind);
		kind.setName("Work Equipment");//8		
		addProductKind(kind);
		kind.setName("Games");//9		
		addProductKind(kind);
		kind.setName("Home Equipment");//10		
		addProductKind(kind);
		kind.setName("Travel");//11		
		addProductKind(kind);
		kind.setName("Houshold");//12		
		addProductKind(kind);
		kind.setName("Beauty/Personal Care");//13		
		addProductKind(kind);
		kind.setName("Children Products");//14		
		addProductKind(kind);
		kind.setName("Mobile Phone Bills");//15		
		addProductKind(kind);
		kind.setName("Phone Bills");//16		
		addProductKind(kind);
		kind.setName("Energy Bills");//17		
		addProductKind(kind);
		kind.setName("Electronics");//18		
		addProductKind(kind);
		kind.setName("Cigarettes");//19		
		addProductKind(kind);
		kind.setName("Vehicles & Parts");//20		
		addProductKind(kind);
		kind.setName("Clothes");//21		
		addProductKind(kind);
		kind.setName("Heyngine");//22		
		addProductKind(kind);
		kind.setName("Pet");//23		
		addProductKind(kind);
		kind.setName("Home Entertainment");//24		
		addProductKind(kind);
		kind.setName("Outside Entertainment");//25		
		addProductKind(kind);
		kind.setName("Other");//26		
		addProductKind(kind);
	}
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
// 
//        // Create tables again
//        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    /*
     * Product Group
     * "CREATE TABLE " +TABLE_PRODUCT_GROUP +"("     
     *	PRODUCT_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
     *  PRODUCT_GROUP_NAME + " TEXT NOT NULL)";
     */
    public void addProductGroup(ProductGroup productGroup){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(PRODUCT_GROUP_NAME, productGroup.getName());
 
        // Inserting Row
        db.insert(TABLE_PRODUCT_GROUP, null, values);
        db.close(); // Closing database connection
    }
    
    public ProductGroup getProductGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PRODUCT_GROUP, new String[] { PRODUCT_GROUP_NAME,}, PRODUCT_GROUP_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        ProductGroup productGroup = new ProductGroup(id, cursor.getString(0));
        cursor.close();
        db.close();
        
        return productGroup;
    }
    
 // Getting All ProductGroup
    public List<ProductGroup> getAllProductGroup() {
        List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_GROUP;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	ProductGroup productGroup = new ProductGroup();
            	productGroup.setId(Integer.parseInt(cursor.getString(0)));
            	productGroup.setName(cursor.getString(1));
                // Adding contact to list
                productGroupList.add(productGroup);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        
        // return product group list
        return productGroupList;
    }
    
 // Updating single ProductGroup
    public int updateProductGroup(ProductGroup productGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(PRODUCT_KIND_NAME, productGroup.getName());
 
        // updating row
        int updateMessage=  db.update(TABLE_PRODUCT_GROUP, values, PRODUCT_GROUP_ID + " = ?",
                new String[] { String.valueOf(productGroup.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single ProductGroup
    public void deleteProductGroup(ProductGroup productGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT_GROUP, PRODUCT_GROUP_ID + " = ?",
                new String[] { String.valueOf(productGroup.getId()) });
        db.close();
    }
 
    // Getting ProductKind
    public int getProductGroupCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT_GROUP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        int count=cursor.getCount();
        db.close();
        return count;
    }
    
    //Product Kind
    public void addProductKind(ProductKind productKind){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(PRODUCT_KIND_NAME, productKind.getName()); // ProductKind Name
        //values.put(PRODUCT_KIND_GROUP_ID, productKind.getGroup_id());
 
        // Inserting Row
        db.insert(TABLE_PRODUCT_KIND, null, values);
        db.close(); // Closing database connection
    }
    
    public ProductKind getProductKind(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PRODUCT_KIND, new String[] { PRODUCT_KIND_NAME,
        		/*PRODUCT_KIND_GROUP_ID,*/}, PRODUCT_KIND_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        ProductKind productKind = new ProductKind(id, cursor.getString(0));
        cursor.close();
        db.close();
        
        return productKind;
    }
    
 // Getting All ProductKind
    public List<ProductKind> getAllProductKind() {
        List<ProductKind> productKindList = new ArrayList<ProductKind>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_KIND;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	ProductKind productKind = new ProductKind();
            	productKind.setId(Integer.parseInt(cursor.getString(0)));
            	productKind.setName(cursor.getString(1));
            	//productKind.setGroup_id(Integer.parseInt(cursor.getString(2)));
                // Adding product kind to list
                productKindList.add(productKind);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        
        // return product kind list
        return productKindList;
    }
    
 // Updating single ProductKind
    public int updateProductKind(ProductKind productKind) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(PRODUCT_KIND_NAME, productKind.getName());
        //values.put(PRODUCT_KIND_GROUP_ID, productKind.getGroup_id());
 
        // updating row
        int updateMessage=  db.update(TABLE_PRODUCT_KIND, values, PRODUCT_KIND_ID + " = ?",
                new String[] { String.valueOf(productKind.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single ProductKind
    public void deleteProductKind(ProductKind productKind) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT_KIND, PRODUCT_KIND_ID + " = ?",
                new String[] { String.valueOf(productKind.getId()) });
        db.close();
    }
 
    // Getting ProductKind
    public int getProductKindCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT_KIND;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        int count=cursor.getCount();
        db.close();
        return count;
    }
    
  /* 
   * "CREATE TABLE " + TABLE_COMMERCIAL_PRODUCT+"("+
   *  COMMERCIAL_PRODUCT_BARCODE+ " INTEGER PRIMARY KEY,"+
   *  COMMERCIAL_PRODUCT_NAME+ " TEXT NOT NULL,"+
   *  COMMERCIAL_PRODUCT_COMPANY_BRAND+" TEXT)";
   *  
   *  Commercial Product
   */
    public void addCommercialProduct(CommercialProduct commercialProduct){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(COMMERCIAL_PRODUCT_BARCODE, commercialProduct.getBarcode());
        values.put(COMMERCIAL_PRODUCT_NAME, commercialProduct.getCommercialName());
        values.put(COMMERCIAL_PRODUCT_COMPANY_BRAND, commercialProduct.getCompanyBrand());
 
        // Inserting Row
        db.insert(TABLE_COMMERCIAL_PRODUCT, null, values);
        db.close(); // Closing database connection
    }
    
    public CommercialProduct getCommercialProduct(String barcode) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_COMMERCIAL_PRODUCT, new String[] { COMMERCIAL_PRODUCT_NAME,
        		COMMERCIAL_PRODUCT_COMPANY_BRAND,}, COMMERCIAL_PRODUCT_BARCODE + "=?",
                new String[] { String.valueOf(barcode) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        CommercialProduct commercialProduct = new CommercialProduct(barcode, cursor.getString(0), cursor.getString(1));
        cursor.close();
        db.close();
        
        return commercialProduct;
    }
    
 // Getting All Commercial Product
    public List<CommercialProduct> getAllCommercialProduct() {
        List<CommercialProduct> commercialProductList = new ArrayList<CommercialProduct>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COMMERCIAL_PRODUCT;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	CommercialProduct commercialProduct = new CommercialProduct();
            	commercialProduct.setBarcode(cursor.getString(0));
            	commercialProduct.setCommercialName(cursor.getString(1));
            	commercialProduct.setCompanyBrand(cursor.getString(2));
                // Adding contact to list
            	commercialProductList.add(commercialProduct);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return commercialProductList;
    }
    
 // Updating single Commercial Product
    public int updateCommercialProduct(CommercialProduct commercialProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(COMMERCIAL_PRODUCT_NAME, commercialProduct.getCommercialName());
        values.put(COMMERCIAL_PRODUCT_COMPANY_BRAND, commercialProduct.getCompanyBrand());
 
        // updating row
        int updateMessage =  db.update(TABLE_COMMERCIAL_PRODUCT, values, COMMERCIAL_PRODUCT_BARCODE + " = ?",
                new String[] { String.valueOf(commercialProduct.getBarcode()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single Commercial Product
    public void deleteCommercialProduct(CommercialProduct commercialProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMERCIAL_PRODUCT, COMMERCIAL_PRODUCT_BARCODE + " = ?",
                new String[] { String.valueOf(commercialProduct.getBarcode()) });
        db.close();
    }
 
    // Getting Commercial Product
    public int getCommercialProductCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COMMERCIAL_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        // return count
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
   /*
    *"CREATE TABLE " + TABLE_PRODUCT +"("+
	*PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
	*PRODUCT_NAME + " TEXT NOT NULL," +
	*PRODUCT_BARCODE + " TEXT NOT NULL," +
	*PRODUCT_KIND+ " INTEGER NOT NULL,"+ 
	*PRODUCT_GROUP_ID_FK+ " INTEGER NOT NULL,"+ 
	*"FOREIGN KEY("+PRODUCT_BARCODE+") REFERENCES "+ TABLE_COMMERCIAL_PRODUCT+"("+COMMERCIAL_PRODUCT_BARCODE+"),"+
	*"FOREIGN KEY("+PRODUCT_KIND+") REFERENCES "+ TABLE_PRODUCT_KIND+"("+PRODUCT_KIND_ID+")"+
	*"FOREIGN KEY("+PRODUCT_GROUP_ID_FK+") REFERENCES "+ TABLE_PRODUCT_GROUP+"("+PRODUCT_GROUP_ID+")"+
	*")"; 
	*Product
    */
    public void addProduct(Product product){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_BARCODE, product.getBarcode());
        values.put(PRODUCT_KIND, product.getKind());
        values.put(PRODUCT_GROUP_ID_FK, product.getKind());
 
        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
    }
    
    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { PRODUCT_NAME, PRODUCT_BARCODE, 
        		PRODUCT_KIND,PRODUCT_GROUP_ID_FK, }, PRODUCT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Product product = new Product(id, cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        cursor.close();
        db.close();
        
        return product;
    }
    
 // Getting All Product
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Product product = new Product();
            	product.setId(cursor.getInt(0));
            	product.setName(cursor.getString(1));
            	product.setBarcode(cursor.getString(2));
            	product.setKind(cursor.getInt(3));
            	product.setGroup(cursor.getInt(4));
                // Adding contact to list
            	productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return productList;
    }
    
 // Updating single Product
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_BARCODE, product.getBarcode());
        values.put(PRODUCT_KIND, product.getKind());
        values.put(PRODUCT_GROUP_ID_FK, product.getKind());
 
        // updating row
        int updateMessage =  db.update(TABLE_PRODUCT, values, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single Product
    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
        db.close();
    }
 
    // Getting Product
    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        // return count
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    /*
     * "CREATE TABLE "+ TABLE_SHOP_DESCRIPTION +"("+
     * SHOP_DESCRIPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
     * SHOP_DESCRIPTION_NAME+ " TEXT NOT NULL)"
     * 
     * Shop Description
     */
    
    public void addShopDescription(ShopDescription shopDescription){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(SHOP_DESCRIPTION_NAME, shopDescription.getName());
 
        // Inserting Row
        db.insert(TABLE_SHOP_DESCRIPTION, null, values);
        db.close(); // Closing database connection
    }
    
    public ShopDescription getShopDescription(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_SHOP_DESCRIPTION, new String[] { SHOP_DESCRIPTION_NAME,}, SHOP_DESCRIPTION_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        ShopDescription shopDescription = new ShopDescription(id, cursor.getString(0));
        cursor.close();
        db.close();
        
        return shopDescription;
    }
    
 // Getting All Shop Description
    public List<ShopDescription> getAllShopDescription() {
        List<ShopDescription> shopDescriptionList = new ArrayList<ShopDescription>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHOP_DESCRIPTION;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	ShopDescription shopDescription = new ShopDescription();
            	shopDescription.setId(cursor.getInt(0));
            	shopDescription.setName(cursor.getString(1));
                // Adding contact to list
            	shopDescriptionList.add(shopDescription);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return shopDescriptionList;
    }
    
 // Updating single Shop Description
    public int updateShopDescription(ShopDescription shopDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(SHOP_DESCRIPTION_NAME, shopDescription.getName());
 
        // updating row
        int updateMessage =  db.update(TABLE_SHOP_DESCRIPTION, values, SHOP_DESCRIPTION_ID + " = ?",
                new String[] { String.valueOf(shopDescription.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single Shop Description
    public void deleteShopDescription(ShopDescription shopDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOP_DESCRIPTION, SHOP_DESCRIPTION_ID + " = ?",
                new String[] { String.valueOf(shopDescription.getId()) });
        db.close();
    }
 
    // Getting Shop Description
    public int getShopDescriptiontCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SHOP_DESCRIPTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        // return count
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    /*"CREATE TABLE "+ TABLE_ADDRESS +"("+
	 *ADDRESS_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ," +
	 *ADDRESS_STREET_NAME+ " TEXT NOT NULL," + 
	 *ADDRESS_NUMBER+ " TEXT,"+
	 *ADDRESS_AREA+ " TEXT,"+
	 *ADDRESS_CITY+ " TEXT,"+
	 *ADDRESS_COUNTY+ " TEXT,"+
	 *ADDRESS_COUNTRY+ " TEXT)";
	 *
	 *Address
	 */
    public void addAddress(Address address){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(ADDRESS_STREET_NAME, address.getStreetName());
        values.put(ADDRESS_NUMBER, address.getNumber());
        values.put(ADDRESS_AREA, address.getArea());
        values.put(ADDRESS_CITY, address.getCity());
        values.put(ADDRESS_COUNTY, address.getCounty());
        values.put(ADDRESS_COUNTRY, address.getCountry());
 
        // Inserting Row
        db.insert(TABLE_ADDRESS, null, values);
        db.close(); // Closing database connection
    }
    
    public Address getAddress(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_ADDRESS, new String[] {ADDRESS_STREET_NAME, ADDRESS_NUMBER, ADDRESS_AREA, ADDRESS_CITY, ADDRESS_COUNTY, 
        		ADDRESS_COUNTRY,}, ADDRESS_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Address address = new Address(id, cursor.getString(0), cursor.getString(1), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        db.close();
        
        return address;
    }
    
 // Getting All Address
    public List<Address> getAllAddress() {
        List<Address> addressList = new ArrayList<Address>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADDRESS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Address address = new Address();
            	address.setId(cursor.getInt(0));
            	address.setStreetName(cursor.getString(1));
            	address.setNumber(cursor.getString(2));
            	address.setArea(cursor.getString(3));
            	address.setCity(cursor.getString(4));
            	address.setCounty(cursor.getString(5));
            	address.setCountry(cursor.getString(6));
                // Adding contact to list
            	addressList.add(address);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return addressList;
    }
    
 // Updating single Address
    public int updateAddress(Address address) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(ADDRESS_STREET_NAME, address.getStreetName());
        values.put(ADDRESS_NUMBER, address.getNumber());
        values.put(ADDRESS_AREA, address.getArea());
        values.put(ADDRESS_CITY, address.getCity());
        values.put(ADDRESS_COUNTY, address.getCounty());
        values.put(ADDRESS_COUNTRY, address.getCountry());
 
        // updating row
        int updateMessage =  db.update(TABLE_ADDRESS, values, ADDRESS_ID + " = ?",
                new String[] { String.valueOf(address.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single Address
    public void deleteCommercialAddress(Address address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADDRESS, ADDRESS_ID + " = ?",
                new String[] { String.valueOf(address.getId()) });
        db.close();
    }
 
    // Getting Address
    public int getAddressCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ADDRESS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        // return count
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    /*
     * "CREATE TABLE "+ TABLE_SHOP +"("+
     *	SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
     *	SHOP_NAME+ " TEXT NOT NULL," + 
     *	SHOP_ADDRESS + " INTEGER NOT NULL," + 
     *	SHOP_DESCRIPTION+" INTEGER NOT NULL," +
     *	"FOREIGN KEY("+SHOP_ADDRESS+") REFERENCES "+ TABLE_ADDRESS+"("+ADDRESS_ID+"),"+
	 *	"FOREIGN KEY("+SHOP_DESCRIPTION+") REFERENCES "+ TABLE_SHOP_DESCRIPTION+"("+SHOP_DESCRIPTION_ID+")"+
	 *	 ")";
	 *
	 *  Shop
     */
    
    public void addShop(Shop shop){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(SHOP_NAME, shop.getName());
        values.put(SHOP_ADDRESS, shop.getAddress());
        values.put(SHOP_DESCRIPTION, shop.getShopDescription());
 
        // Inserting Row
        db.insert(TABLE_SHOP, null, values);
        db.close(); // Closing database connection
    }
    
    public Shop getShop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_SHOP, new String[] { SHOP_NAME, SHOP_ADDRESS, 
        		SHOP_DESCRIPTION,}, PRODUCT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Shop shop = new Shop(id, cursor.getInt(1), cursor.getInt(2), cursor.getString(0));
        cursor.close();
        db.close();
        
        return shop;
    }
    
 // Getting All Shop
    public List<Shop> getAllShop() {
        List<Shop> shopList = new ArrayList<Shop>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHOP;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Shop shop = new Shop();
            	shop.setId(cursor.getInt(0));
            	shop.setName(cursor.getString(1));
            	shop.setAddress(cursor.getInt(2));
            	shop.setShopDescription(cursor.getInt(3));
                // Adding contact to list
            	shopList.add(shop);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return shopList;
    }
    
 // Updating single Shop
    public int updateShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(SHOP_NAME, shop.getName());
        values.put(SHOP_ADDRESS, shop.getAddress());
        values.put(SHOP_DESCRIPTION, shop.getShopDescription());
 
        // updating row
        int updateMessage =  db.update(TABLE_SHOP, values, SHOP_ID + " = ?",
                new String[] { String.valueOf(shop.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single Shop
    public void deleteShop(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
        db.close();
    }
 
    // Getting Product
    public int getShopCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        // return count
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    /*
     * "CREATE TABLE "+ TABLE_BUYS +"("+
	 *	 BUYS_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ," +
	 *	 BUYS_PRODUCT + " INTEGER NOT NULL," +
	 *	 BUYS_SHOP + " INTEGER NOT NULL," +
	 *	 BUYS_UNIT_PRICE + " REAL NOT NULL," +
	 *	 BUYS_AMOUNT + " INTEGER NOT NULL," +
	 *	 BUYS_DATE + " TEXT NOT NULL," +
	 *	 "FOREIGN KEY("+BUYS_PRODUCT+") REFERENCES "+ TABLE_PRODUCT+"("+PRODUCT_ID+"),"+
	 *	 "FOREIGN KEY("+BUYS_SHOP+") REFERENCES "+ TABLE_SHOP+"("+SHOP_ID+")"+
	 *	 ")";
	 *
	 * Buys
     */
    
    public void addBuys(Buys buys){
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(BUYS_PRODUCT, buys.getProduct());
        values.put(BUYS_SHOP, buys.getShop());
        values.put(BUYS_UNIT_PRICE, buys.getUnit_price());
        values.put(BUYS_AMOUNT, buys.getAmount());
        values.put(BUYS_DATE, buys.getDate());
 
        // Inserting Row
        db.insert(TABLE_BUYS, null, values);
        db.close(); // Closing database connection
    }
    
    public Buys getBuys(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_BUYS, new String[] { BUYS_PRODUCT, BUYS_SHOP, 
        		BUYS_UNIT_PRICE, BUYS_AMOUNT, BUYS_DATE,  }, BUYS_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Buys buys = new Buys(id, cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2), cursor.getInt(3), cursor.getString(4));
        cursor.close();
        db.close();
        
        return buys;
    }
    
 // Getting All Buys
    public List<Buys> getAllBuys() {
        List<Buys> buysList = new ArrayList<Buys>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BUYS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Buys buys = new Buys();
            	buys.setId(cursor.getInt(0));
            	buys.setProduct(cursor.getInt(1));
            	buys.setShop(cursor.getInt(2));
            	buys.setUnit_price(cursor.getDouble(3));
            	buys.setAmount(cursor.getInt(4));
            	buys.setDate(cursor.getString(5));
            	buys.setUser(-1);
            	
                // Adding contact to list
            	buysList.add(buys);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return buysList;
    }
    
 // Updating single Buys
    public int updateBuys(Buys buys) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(BUYS_PRODUCT, buys.getProduct());
        values.put(BUYS_SHOP, buys.getShop());
        values.put(BUYS_UNIT_PRICE, buys.getUnit_price());
        values.put(BUYS_AMOUNT, buys.getAmount());
        values.put(BUYS_DATE, buys.getDate());
 
        // updating row
        int updateMessage =  db.update(TABLE_BUYS, values, BUYS_ID + " = ?",
                new String[] { String.valueOf(buys.getId()) });
        db.close();
        return updateMessage;
    }
 
    // Deleting single Buys
    public void deleteBuys(Buys buys) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUYS, BUYS_ID + " = ?",
                new String[] { String.valueOf(buys.getId()) });
        db.close();
    }
 
    // Getting Product
    public int getBuysCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BUYS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        // return count
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    							 
    							
}
