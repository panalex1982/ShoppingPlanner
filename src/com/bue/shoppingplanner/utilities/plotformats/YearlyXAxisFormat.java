package com.bue.shoppingplanner.utilities.plotformats;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YearlyXAxisFormat extends Format {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

        Date date = new Date(((Number) obj).longValue());
        return dateFormat.format(date, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return null;

    }
}
