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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_stats_categories, container);
		Bundle tab1Bundle=new Bundle();
		tab1Bundle.putInt("chosenTab", 1);
		Bundle tab2Bundle=new Bundle();
		tab2Bundle.putInt("chosenTab", 2);
		Bundle tab3Bundle=new Bundle();
		tab3Bundle.putInt("chosenTab", 3);
		Bundle tab4Bundle=new Bundle();
		tab4Bundle.putInt("chosenTab", 4);
		
		String buyer=getResources().getString(R.string.buyer);
		String shop=getResources().getString(R.string.shop);
		String product=getResources().getString(R.string.product);
		String kind=getResources().getString(R.string.product_kind);
		
		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		
        mTabHost.setup(getActivity(), getFragmentManager(),R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("buyer").setIndicator(buyer),
                StatsMainFragment.class,tab1Bundle);
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
	
}
