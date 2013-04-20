package com.bue.shoppingplanner.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

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
}
