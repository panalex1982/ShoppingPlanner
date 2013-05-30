package com.bue.shoppingplanner.utilities.plotformats;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeeklyXAxisFormat extends Format {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
	
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
