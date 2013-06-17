package com.bue.shoppingplanner.free.utilities;

import java.sql.Timestamp;

/**
 * This class contains common methods needed in the project. 
 * Most of the methods make conversions.
 */
public class Utilities {
	public static Timestamp convertStringToSqlTimestamp(String date){
		Timestamp ts = Timestamp.valueOf(date);
		return ts;
	}

}
