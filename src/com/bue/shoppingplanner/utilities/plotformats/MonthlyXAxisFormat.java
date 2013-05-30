package com.bue.shoppingplanner.utilities.plotformats;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonthlyXAxisFormat extends Format {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
	
	@Override
	public StringBuffer format(Object object, StringBuffer buffer,
			FieldPosition field) {
		Date date = new Date(((Number) object).longValue());
        return dateFormat.format(date, buffer, field);
	}

	@Override
	public Object parseObject(String string, ParsePosition position) {
		// TODO Auto-generated method stub
		return null;
	}

}
