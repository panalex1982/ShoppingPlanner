package com.bue.shoppingplanner.free.helpers;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.bue.shoppingplanner.free.views.dialogs.AboutDialogFragment;
import com.bue.shoppingplanner.free.views.dialogs.AddProductDialogFragment;

public class DialogOpener {
	/**
	 * Opens dialog fragment to add new product to the list.
	 */
	public static void showAddProductDialog(FragmentManager manager, boolean isOnlyProduct) {
        // Create an instance of the dialog fragment and show it		
		DialogFragment dialog = new AddProductDialogFragment();
		Bundle barcodeBundle=new Bundle();
		barcodeBundle.putString("barcode", "no_barcode");
		barcodeBundle.putBoolean("isOnlyProduct", isOnlyProduct);
		dialog.setArguments(barcodeBundle);
        dialog.show(manager, "AddProductDialogFragment");        
    }
	
	/**
	 * Opens dialog fragment to add new product to the list.
	 * @param barcode -provides barcode to load existing product or add new commercial product.
	 * @param isOnlyProduct - set true if this is used to add on;y a product and bought
	 */
	public static void showAddProductDialog(FragmentManager manager, String barcode, boolean isOnlyProduct) {
        // Create an instance of the dialog fragment and show it
		DialogFragment dialog = new AddProductDialogFragment();
		Bundle barcodeBundle=new Bundle();
		barcodeBundle.putString("barcode", barcode);
		barcodeBundle.putBoolean("isOnlyProduct", isOnlyProduct);
		dialog.setArguments(barcodeBundle);
        dialog.show(manager, "AddProductDialogFragment");        
    }
	
	/**
	 * Shows about dialog.
	 * @param manager
	 */
	public static void showAboutDialog(FragmentManager manager){
		AboutDialogFragment dialog = new AboutDialogFragment();
	    dialog.show(manager, "AboutDialogFragment");
	}
}
