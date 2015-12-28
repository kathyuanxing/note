
package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testandroid.R;

public class MyFragment4 extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		View view=inflater.inflate(R.layout.fragment_profile, container,false);
		
		Log.d("","view");
		
		return view;
	}

}
