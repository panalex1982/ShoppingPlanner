package com.bue.shoppingplanner.fragments;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.views.StatsArrayAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StatsContentFragment extends ListFragment {
	
	//private ListView groupStatsListView;
	private StatsArrayAdapter groupStatsListAdapter;
	
	private ArrayList<String[]> totalsBy;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//View statsContentView=getActivity().findViewById(R.id.statsContentFragmentList);
		Bundle chosenTab=getArguments();
		Log.d("Bundle: ",String.valueOf(chosenTab.getInt("chosenTab")));
		BoughtController boughtController=new BoughtController(getActivity());
		switch(chosenTab.getInt("chosenTab")){
		case 1:
			totalsBy=boughtController.getTotalByGroup();
			break;
		case 2:
			totalsBy=boughtController.getTotalByProduct();
			break;
		case 3:
			totalsBy=boughtController.getTotalByShop();
			break;
		case 4:
			totalsBy=boughtController.getTotalByKind();
			break;
		default:
			totalsBy=boughtController.getTotalByGroup();
		}
		
		groupStatsListAdapter=new StatsArrayAdapter(getActivity(),R.layout.stats_element_view,totalsBy);
		setListAdapter(groupStatsListAdapter);
		//return statsContentView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d("Item Selected", totalsBy.get(position)[0]);
	}
	
	
	

}
