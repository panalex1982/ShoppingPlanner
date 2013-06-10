package com.bue.shoppingplanner.utilities;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.utilities.CameraPreview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ScanBarcode extends FragmentActivity{

	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    TextView scanText;
    Button scanButton;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;
    
    private String scan;

    static {
        System.loadLibrary("iconv");
    } 

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.camera_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        scan="";
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new OnClickListener() {
                @Override
				public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            });
    }

    @Override
	public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        	Log.d("Can't open camera: ",e.toString());
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
                    if(syms.size()<20)
	                    for (Symbol sym : syms) {
	                        scanText.setText("barcode result " + sym.getData());
	                        scan=scan+sym.getData();
	                        barcodeScanned = true;
	                    }
                }
                if(barcodeScanned){
                	//Bundle barcodeBundle=new Bundle();
                	//barcodeBundle.putString("lastBarcodeScan", scan);
                	Intent rIntent=getIntent();
                	rIntent.putExtra("lastBarcodeScan", scan);
                	setResult(1,rIntent);
                	finish();
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

}
