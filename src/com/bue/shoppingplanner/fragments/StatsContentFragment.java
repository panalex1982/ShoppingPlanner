package com.bue.shoppingplanner.fragments;

import java.util.ArrayList;

import com.bue.shoppingplanner.R;
import com.bue.shoppingplanner.controllers.BoughtController;
import com.bue.shoppingplanner.views.StatsExpandableListAdapter;

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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StatsContentFragment extends Fragment {
	
	private ExpandableListView statsItemsExpandableListView;
	private StatsExpandableListAdapter groupStatsListAdapter;
	
	
	private ArrayList<String[]> totalsBy;
	private ArrayList<ArrayList<String[]>> childTotalsBy;
	
	private String listKey;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//View statsContentView=getActivity().findViewById(R.id.statsContentFragmentList);
		Bundle chosenTab=getArguments();
		childTotalsBy=new ArrayList<ArrayList<String[]>>();
		BoughtController boughtController=new BoughtController(getActivity());
		switch(chosenTab.getInt("chosenTab")){
		case 1:
			totalsBy=boughtController.getTotalByGroup();
			for(String[] total:totalsBy){
				ArrayList<String[]> tmpList=boughtController.getGroupSpendingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		case 2:
			totalsBy=boughtController.getTotalByProduct();
			for(String[] total:totalsBy){
				ArrayList<String[]> tmpList=boughtController.getProductSpedingByShop(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		case 3:
			totalsBy=boughtController.getTotalByShop();
			for(String[] total:totalsBy){
				ArrayList<String[]> tmpList=new ArrayList<String[]>();
				tmpList=boughtController.getShopSpedingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		case 4:
			totalsBy=boughtController.getTotalByKind();
			for(String[] total:totalsBy){
				ArrayList<String[]> tmpList=new ArrayList<String[]>();
				tmpList=boughtController.getKindSpendingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
			break;
		default:
			totalsBy=boughtController.getTotalByGroup();
			for(String[] total:totalsBy){
				ArrayList<String[]> tmpList=new ArrayList<String[]>();
				tmpList=boughtController.getGroupSpendingByProduct(total[0]);
				childTotalsBy.add(tmpList);
			}
		}
		
		//return statsContentView;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_stats_content, container,false);
		statsItemsExpandableListView=(ExpandableListView) view.findViewById(R.id.statsItemsExpandableListView);
		LinearLayout headerLayout=new LinearLayout(getActivity());
		TextView headerText=new TextView(getActivity());
		headerText.setText(listKey);
		headerLayout.addView(headerText);
		statsItemsExpandableListView.addHeaderView(headerLayout);
		groupStatsListAdapter=new StatsExpandableListAdapter(getActivity(),R.layout.stats_element_view,totalsBy, childTotalsBy);
		statsItemsExpandableListView.setAdapter(groupStatsListAdapter);	
		return view;
	}



//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//			
//	}



	/*@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d("Item Selected", totalsBy.get(position-1)[0]);
		Bundle bundle=new Bundle();
		bundle.putInt("chosenTab", listGroup);
		bundle.putString("listKey", totalsBy.get(position-1)[0]);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		StatsContentFragment fragment=new StatsContentFragment();
		fragment.setArguments(bundle);
		fragmentTransaction.replace(this.getId(), fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		//Errors:has problem when I change tab
}*/
	
	
	

}
