
package com.example.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.adapter.ContactAdapter;
import com.example.dao.ContactDao;
import com.example.entity.ChatMsgEntity;
import com.example.entity.Contact;
import com.example.entity.ContactRow;
import com.example.util.Constants;

import com.example.testandroid.R;
import com.example.widget.SideBar;
import java.util.ArrayList;
import java.util.List;


public class MyFragment2 extends ListFragment implements OnItemClickListener {
	private Activity ctx;
	private View layout, layout_head;
	private SideBar indexBar;
	private ContactAdapter contactAdapter;
	private Contact contact;
	private ListView list;//联系人列表
	private List<Contact> contactArrayList=new ArrayList<Contact>();
	private List<ContactRow> contactRowList=new ArrayList<ContactRow>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_friends, container, false);
		initViews();
		initData();

		return view;
	}
	@Override
	public void onViewCreated (View view, Bundle savedInstanceState) {
		list = getListView();
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		if (contactArrayList != null && !contactArrayList.isEmpty()) {
			contactAdapter = new ContactAdapter(ctx, contactRowList);
			list.setAdapter(contactAdapter);
		}else {
			Toast.makeText(ctx, "empty", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

		Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();

	}
	private void initViews() {
		ctx = this.getActivity();
		layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
				null);
		indexBar = (SideBar) layout.findViewById(R.id.sideBar);
		indexBar.setListView(list);
	}

	private void initData() {
		ContactDao mContactDao = new ContactDao(ctx);
		mContactDao.open();
		contactArrayList = mContactDao.getAllContacts();
		for (Contact j : contactArrayList) {
		    //遍历contactArrayList
		    ContactRow eachEntity =new ContactRow();
			eachEntity.setTitle(j.getContact_name());
			eachEntity.setIcon(R.drawable.head);
			contactRowList.add(eachEntity);
//			Log.d("eachEntity",eachEntity.toString());
		}
		Log.d("contactRowList",contactRowList.toString());
	}
}
