package com.bue.shoppingplanner.views.fragments;

import com.bue.shoppingplanner.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsCategoriesFragment extends Fragment {
	private FragmentTabHost mTabHost;
	private int buyerFragmentId = 0xffffffc0, productFragmentId = 0xffffffc1,
			shopFragmentId = 0xffffffc2, kindFragmentId = 0xffffffc3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stats_categories,
				container);
		Bundle tab1Bundle = new Bundle();
		tab1Bundle.putInt("chosenTab", 1);
		Bundle tab2Bundle = new Bundle();
		tab2Bundle.putInt("chosenTab", 2);
		Bundle tab3Bundle = new Bundle();
		tab3Bundle.putInt("chosenTab", 3);
		Bundle tab4Bundle = new Bundle();
		tab4Bundle.putInt("chosenTab", 4);

		String buyer = getResources().getString(R.string.buyer);
		String shop = getResources().getString(R.string.shop);
		String product = getResources().getString(R.string.product);
		String kind = getResources().getString(R.string.product_kind);

		// Create Fragments
		StatsMainFragment buyerFragment = new StatsMainFragment();
		StatsMainFragment productFragment = new StatsMainFragment();
		StatsMainFragment shopFragment = new StatsMainFragment();
		StatsMainFragment kindFragment = new StatsMainFragment();
		
//		setFragmetIds();
//		getFragmentManager().beginTransaction()
//			.add(buyerFragmentId, buyerFragment)
//			.add(productFragmentId, productFragment)
//			.add(shopFragmentId, shopFragment)
//			.add(kindFragmentId, kindFragment)
//			.commit();
//        getFragmentManager().executePendingTransactions();

		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

		mTabHost.setup(getActivity(), getFragmentManager(), R.id.realtabcontent);

		mTabHost.addTab(mTabHost.newTabSpec("buyer").setIndicator(buyer),
				StatsMainFragment.class, tab1Bundle);
		mTabHost.addTab(mTabHost.newTabSpec("product").setIndicator(product),
				StatsMainFragment.class, tab2Bundle);
		mTabHost.addTab(mTabHost.newTabSpec("shop").setIndicator(shop),
				StatsMainFragment.class, tab3Bundle);
		mTabHost.addTab(mTabHost.newTabSpec("kind").setIndicator(kind),
				StatsMainFragment.class, tab4Bundle);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private boolean checkIdExistance(int id) {
		String name = getResources().getResourceName(id);
		if (name == null)
			return false;
		else
			return true;
	}

	private void setFragmetIds() {
		while (checkIdExistance(buyerFragmentId)) {
			buyerFragmentId += 1;
		}
		if (buyerFragmentId != 0xffffffc0) {
			productFragmentId = buyerFragmentId + 1;
			while (checkIdExistance(productFragmentId)) {
				productFragmentId += 1;
			}
			shopFragmentId = productFragmentId + 1;
			while (checkIdExistance(shopFragmentId)) {
				shopFragmentId += 1;
			}
			kindFragmentId = shopFragmentId + 1;
			while (checkIdExistance(kindFragmentId)) {
				kindFragmentId += 1;
			}
		}
	}

}
