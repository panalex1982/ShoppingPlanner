package com.bue.shoppingplanner.free.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class SerializeObject {
	
	public static void write(Context context, Object object, String path) throws IOException{
		FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(object);
		os.close();
	}
	
	public static Object read(Context context, String path)throws IOException, ClassNotFoundException{
		FileInputStream fis = context.openFileInput(path);
		ObjectInputStream is = new ObjectInputStream(fis);
		Object object = is.readObject();
		is.close();
		return object;	
	}
	
	//exporting database 
    public static void exportDB(String path, Context context) {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            Log.d("Path SD: ",sd.getAbsolutePath());
            if (sd.canWrite()) {
            	Log.d("Path SD(2): ",sd.getAbsolutePath());
                String  currentDBPath= "//data//" + "com.bue.shoppingplanner"
                        + "//databases//" + "shoppingPlannerDB";
                String backupDBPath  = path;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(backupDBPath);
                if(!backupDB.exists()){
                	backupDB.createNewFile();
                }

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
        	Log.d("Can't save: ",e.toString());
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
    
  //importing database
    public static void importDB(String path, Context context) {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "com.bue.shoppingplanner"
                        + "//databases//" + "shoppingPlannerDB";
                File  backupDB= new File(path);
                File currentDB  = new File(data, currentDBPath);
                

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
}
