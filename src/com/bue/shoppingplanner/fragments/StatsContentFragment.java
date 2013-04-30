package com.bue.shoppingplanner.fragments;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.views.StatsArrayAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StatsContentFragment extends ListFragment {
	
	//private ListView groupStatsListView;
	private StatsArrayAdapter groupStatsListAdapter;
	
	private ArrayList<String[]> totalsBy;
	
	private int listGroup;
	private String listKey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//View statsContentView=getActivity().findViewById(R.id.statsContentFragmentList);
		Bundle chosenTab=getArguments();
		BoughtController boughtController=new BoughtController(getActivity());
		listKey=chosenTab.getString("listKey","");
		switch(chosenTab.getInt("chosenTab")){
		case 1:
			totalsBy=boughtController.getTotalByGroup();
			listGroup=5;
			break;
		case 2:
			totalsBy=boughtController.getTotalByProduct();
			listGroup=6;
			break;
		case 3:
			totalsBy=boughtController.getTotalByShop();
			listGroup=7;
			break;
		case 4:
			totalsBy=boughtController.getTotalByKind();
			listGroup=8;
			break;
		case 5:
			totalsBy=boughtController.getGroupSpendingByProduct(listKey);
			listGroup=8;
			break;
		default:
			totalsBy=boughtController.getTotalByGroup();
			listGroup=chosenTab.getInt("chosenTab");
		}
		
		//return statsContentView;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LinearLayout headerLayout=new LinearLayout(getActivity());
		TextView headerText=new TextView(getActivity());
		headerText.setText(listKey);
		headerLayout.addView(headerText);
		this.getListView().addHeaderView(headerLayout);
		groupStatsListAdapter=new StatsArrayAdapter(getActivity(),R.layout.stats_element_view,totalsBy);
		setListAdapter(groupStatsListAdapter);
	}



	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d("Item Selected", totalsBy.get(position)[0]);
		Bundle bundle=new Bundle();
		bundle.putInt("chosenTab", listGroup);
		bundle.putString("listKey", totalsBy.get(position)[0]);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		StatsContentFragment fragment=new StatsContentFragment();
		fragment.setArguments(bundle);
		fragmentTransaction.replace(this.getId(), fragment);
		fragmentTransaction.commit();
		//Errors: gets Wrong Item and has problem when I change tab
	}
	
	
	

}
