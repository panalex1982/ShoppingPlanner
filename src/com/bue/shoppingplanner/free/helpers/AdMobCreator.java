package com.bue.shoppingplanner.free.helpers;

import android.app.Activity;
import android.widget.LinearLayout;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.utilities.Keys;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdMobCreator {
	public static void createAd(Activity activity, AdView adview, int layoutId) {
		// Create the adView
		adview = new AdView(activity, AdSize.BANNER, Keys.AD_MOB_KEY);
		// Lookup your LinearLayout assuming it's been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) activity.findViewById(layoutId);

		// Add the adView to it
		layout.addView(adview);
		
		// Initiate a generic request to load it with an ad
		AdRequest adRequest = new AdRequest();
		//adRequest.addTestDevice(AdRequest.TEST_EMULATOR);               // Emulator                      // Test Android Device
		// Initiate a generic request to load it with an ad
		adview.loadAd(adRequest);
	}
	
	public static void destroyAd(AdView adview){
		if (adview != null) {
			adview.destroy();
		}
	}
}
