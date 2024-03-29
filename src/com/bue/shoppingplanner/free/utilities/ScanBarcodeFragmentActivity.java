package com.bue.shoppingplanner.free.utilities;

import java.util.Date;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.utilities.CameraPreview;
import com.bue.shoppingplanner.free.views.PriceInTimeChartActivity;
import com.bue.shoppingplanner.free.views.dialogs.AddProductDialogFragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScanBarcodeFragmentActivity extends FragmentActivity implements
		SPSharedPreferences {

	private Camera mCamera;
	private CameraPreview mPreview;
	private Handler autoFocusHandler;

	private TextView scanText;
	private Button scanButton;

	private ImageScanner scanner;

	private boolean barcodeScanned = false;
	private boolean previewing = true;

	private String scan;

	// Second way to pass barcode
	// private SharedPreferences barcodeShare;

	static {
		System.loadLibrary("iconv");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_main);
		scanButton=(Button) findViewById(R.id.scanButton);
		scanText = (TextView) findViewById(R.id.scanText);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		scan = "";
		autoFocusHandler = new Handler();
		mCamera = getCameraInstance();		

		/* Instance barcode scanner */
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);
		
		if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)){
			scanButton.setVisibility(View.INVISIBLE);
			mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
		}else{			
			mPreview=new CameraPreview(this, mCamera, null, null);
			Toast.makeText(
					ScanBarcodeFragmentActivity.this, getResources().getString(R.string.no_autofocus), Toast.LENGTH_LONG).show();
		}
		FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
		preview.addView(mPreview);

		
		
		scanButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCamera.takePicture(new Camera.ShutterCallback() {
						    public void onShutter() {
						      // Play your sound here.
						    }
						  }, takePictureCallback, null);
				
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		releaseCamera();
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
			Log.d("Can't open camera: ", e.toString());
		}
		return c;
	}

	private void releaseCamera() {
		if (mCamera != null) {
			previewing = false;
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
	}

	private Runnable doAutoFocus = new Runnable() {
		@Override
		public void run() {
			if (previewing)
				mCamera.autoFocus(autoFocusCB);
		}
	};

	PreviewCallback previewCb = new PreviewCallback() {
		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			Camera.Parameters parameters = camera.getParameters();
			Size size = parameters.getPreviewSize();

			Image barcode = new Image(size.width, size.height, "Y800");
			barcode.setData(data);

			int result = scanner.scanImage(barcode);

			if (result != 0) {
				previewing = false;
				mCamera.setPreviewCallback(null);
				mCamera.stopPreview();

				SymbolSet syms = scanner.getResults();
				if (syms.size() < 20)
					for (Symbol sym : syms) {
						scanText.setText("barcode result " + sym.getData());
						scan = scan + sym.getData();
						barcodeScanned = true;
					}
			}
			if (barcodeScanned) {
				returnToMain();
			}
		}
	};

	// Mimic continuous auto-focusing
	AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			autoFocusHandler.postDelayed(doAutoFocus, 1000);
		}
	};
	
	PictureCallback takePictureCallback=new PictureCallback(){

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			scanPicture(data, camera);
			
		}
		
	};
	
	private void scanPicture(byte[] data, Camera camera){
		Camera.Parameters parameters = camera.getParameters();
		Size size = parameters.getPreviewSize();

		Image barcode = new Image(size.width, size.height, "Y800");
		barcode.setData(data);

		int result = scanner.scanImage(barcode);

		if (result != 0) {
			

			SymbolSet syms = scanner.getResults();
			if (syms.size() < 20)
				for (Symbol sym : syms) {
					scanText.setText("barcode result " + sym.getData());
					scan = scan + sym.getData();
					barcodeScanned = true;
				}
		}
		if (barcodeScanned) {
			previewing = false;
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			returnToMain();
		}else{
			
		}
	}

	private void returnToMain() {
		// Bundle barcodeBundle=new Bundle();
		// barcodeBundle.putString("lastBarcodeScan", scan);
		Intent rIntent = getIntent();
		rIntent.putExtra("lastBarcodeScan", scan);
		setResult(1, rIntent);
		// Second way to pass barcode
		// SharedPreferences.Editor editor=barcodeShare.edit();
		// editor.putString(SCANNED_BARCODE, scan);
		// editor.commit();
		// setResult(1);
		finish();
	}

}
