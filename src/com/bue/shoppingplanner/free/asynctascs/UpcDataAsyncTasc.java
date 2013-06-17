package com.bue.shoppingplanner.free.asynctascs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.bue.shoppingplanner.free.IntroActivity;
import com.bue.shoppingplanner.free.helpers.ShoppingListElementHelper;
import com.bue.shoppingplanner.free.model.Currencies;

import android.os.AsyncTask;
import android.util.Log;

public class UpcDataAsyncTasc extends AsyncTask<String, Integer, ShoppingListElementHelper>{

	@Override
	protected ShoppingListElementHelper doInBackground(String... params) {
		return setScannedProduct(params[0]);
	}
	
	private ShoppingListElementHelper setScannedProduct(String url){
		ShoppingListElementHelper product=new ShoppingListElementHelper();
		 String readExchangesFeed = readBarcodeFeed(url);
		    try {
		      JSONObject jsonObject = new JSONObject(readExchangesFeed);
		        if(jsonObject.getString("valid").equals("true")){
		        	product.setBarcode(jsonObject.getString("number"));
		        	product.setProduct(jsonObject.getString("itemname"));
		        	product.setBrand(jsonObject.getString("description"));
		        }else{
		        	product.setBarcode("invalid");
		        }		        
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return product;
	}
	
	private String readBarcodeFeed(String url) {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e(IntroActivity.class.toString(), "Failed to download file");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }catch (Exception e) {
		      e.printStackTrace();
		    }
	    return builder.toString();
	  }

}
