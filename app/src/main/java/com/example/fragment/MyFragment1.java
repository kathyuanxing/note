
package com.example.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.testandroid.R;

public class MyFragment1 extends ListFragment implements OnItemClickListener {
	private ListView listView;
	private Activity ctx;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		View view=inflater.inflate(R.layout.fragment_msg, container,false);
		initViews();
		initData();
		Log.d("","view");
		
		return view;
	}
	@Override
	public void onViewCreated (View view, Bundle savedInstanceState) {
		listView = getListView();
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
	public void initViews(){
		ctx = this.getActivity();
	}
	public void initData(){

	}

}
