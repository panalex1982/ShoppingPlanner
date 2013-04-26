package com.bue.shoppingplanner.fragments;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.views.StatsArrayAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class StatsContentFragment extends ListFragment {
	
	//private ListView groupStatsListView;
	private StatsArrayAdapter groupStatsListAdapter;
	
	private ArrayList<String[]> totalsByGroup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//View statsContentView=getActivity().findViewById(R.id.statsContentFragmentList);
		BoughtController boughtController=new BoughtController(getActivity());
		totalsByGroup=boughtController.getTotalByGroup();
		groupStatsListAdapter=new StatsArrayAdapter(getActivity(),R.layout.stats_element_view,totalsByGroup);
		setListAdapter(groupStatsListAdapter);
		//return statsContentView;
	}
	

}
