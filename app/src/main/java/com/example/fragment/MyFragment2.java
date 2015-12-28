
package com.example.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.util.Constants;

import com.example.testandroid.R;
import com.example.widget.SideBar;

/**
ͨѶ¼
*/
public class MyFragment2 extends Fragment {
	private Activity ctx;
	private ListView lvContact;
	private View layout, layout_head;
	private SideBar indexBar;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		View view = inflater.inflate(R.layout.fragment_friends, container, false);
		initViews();
		initData();
		Log.d("", "view");

		return view;
	}

	private void initViews() {
		ctx = this.getActivity();
		layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
				null);
		lvContact = (ListView) layout.findViewById(R.id.lvContact);
		indexBar = (SideBar) layout.findViewById(R.id.sideBar);
		indexBar.setListView(lvContact);
	}

	private void initData() {
	}
}
