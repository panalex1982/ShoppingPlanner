package com.bue.shoppingplanner.free.views.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableRow;

public class HiddingTableRow extends TableRow {
	
	private boolean isVisible;

	public HiddingTableRow(Context context) {
		super(context);
		isVisible=true;
	}
	
	public HiddingTableRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		isVisible=true;
	}



	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
