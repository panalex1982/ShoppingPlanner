package com.bue.shoppingplanner.free.views.adapters;

import java.util.Currency;
import java.util.List;

import com.bue.shoppingplanner.free.R;
import com.bue.shoppingplanner.free.controllers.CurrencyController;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatsArrayAdapter extends ArrayAdapter<String[]> {

	protected Context context;
	protected int layoutResourceId;
	protected List<String[]> data;
	private CurrencyController cController;

	public StatsArrayAdapter(Context context, int textViewResourceId,
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

			holder.group = (TextView) row
					.findViewById(R.id.nameStatsElementTextView);
			holder.amount = (TextView) row
					.findViewById(R.id.amountStatsElementTextView);
			holder.currency = (TextView) row
					.findViewById(R.id.currencyStatsElementTextView);
			row.setTag(holder);
		} else {
			holder = (StatsElementHolder) row.getTag();
		}
		String[] rowData = data.get(position);
		holder.group.setText(rowData[0]);
		holder.amount.setText(cController.formatCurrecy(rowData[1]));
		holder.currency.setText(cController.getCurrencySymbol());

		return row;
	}

	private class StatsElementHolder {
		TextView group, amount, currency;
	}

}