package com.bue.shoppingplanner.views.adapters;

import java.util.List;
import java.util.Locale;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.CurrencyController;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExchangeRatesArrayAdapter extends ArrayAdapter<String[]> {
	protected Context context;
	protected int layoutResourceId;
	protected List<String[]> data;
	private CurrencyController cController;

	public ExchangeRatesArrayAdapter(Context context, int textViewResourceId,
			List<String[]> data) {
		super(context, textViewResourceId, data);
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.data = data;
		cController=new CurrencyController(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		StatsElementHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((FragmentActivity) context)
					.getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new StatsElementHolder();

			holder.flag = (ImageView) row
					.findViewById(R.id.flagRateElementImageView);
			holder.amount = (TextView) row
					.findViewById(R.id.rateRateElementTextView);
			holder.currency = (TextView) row
					.findViewById(R.id.currencyRateElementTextView);
			row.setTag(holder);
		} else {
			holder = (StatsElementHolder) row.getTag();
		}
		String[] rowData = data.get(position);
		String country=rowData[2].substring(0, 2);
		//country=country.toLowerCase(Locale.US);
		int resourceId=context.getResources().getIdentifier("eu", "drawable", context.getPackageName());
		try{
			holder.flag.setBackgroundResource(resourceId);
		}catch(Exception ex){
			holder.flag.setBackgroundResource(context.getResources().getIdentifier("unknown", "drawable", context.getPackageName()));
		}
		holder.amount.setText(cController.formatCurrecy(rowData[0]));
		holder.currency.setText(cController.getCurrencySymbol(rowData[1]));

		return row;
	}

	private class StatsElementHolder {
		ImageView flag;
		TextView amount, currency;
	}
}
