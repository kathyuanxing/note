
package com.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.example.adapter.HistoryAdapter;
import com.example.dao.MDatabaseConstants;
import com.example.dao.MessageDao;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.entity.ChatRow;
import com.example.entity.MMessage;
import com.example.testandroid.R;
import com.example.testandroid.RegisterSuccessActivity;

import java.util.ArrayList;
import java.util.List;

public class MyFragment1 extends ListFragment implements OnItemClickListener {
	private ListView listView;
	private Activity ctx;
	private HistoryAdapter historyAdapter;
	List<MMessage> messages=new ArrayList<MMessage>() ;
	List<ChatRow> chatRowList=new ArrayList<ChatRow>();
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
			historyAdapter=new HistoryAdapter(ctx,chatRowList);
			listView.setAdapter(historyAdapter);
			listView.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String itemTitle,itemID;
		MMessage chatFriend =messages.get(position);
		itemTitle=chatFriend.getReceiverName();
		itemID=chatFriend.getReceiverID();
		Intent intent=new Intent(ctx, RegisterSuccessActivity.class);
		intent.putExtra("itemTitle",itemTitle);
		intent.putExtra("itemID",itemID);
		startActivity(intent);
	}
	public void initViews(){
		ctx = this.getActivity();
	}
	public void initData(){
		MessageDao messageDao=new MessageDao(ctx);
		messages=messageDao.getRecentMessage();
		for (MMessage j :messages ) {
			//遍历messages
			ChatRow eachEntity =new ChatRow();
			eachEntity.setChatName(j.getReceiverName());
			eachEntity.setChatHead(R.drawable.head);
//			eachEntity.setChatState(0);
			eachEntity.setChatTime(j.getTime());
			switch (j.getType()) {
				case MDatabaseConstants.MESSAGE_TYPE_TEXT:
				eachEntity.setChatMessage(j.getMessageText());
					break;
				case MDatabaseConstants.MESSAGE_TYPE_AUDIO:
					eachEntity.setChatMessage("[语音]");
					break;
				case MDatabaseConstants.MESSAGE_TYPE_IMAGE:
					eachEntity.setChatMessage("[图片]");
					break;
				case MDatabaseConstants.MESSAGE_TYPE_VIDEO:
					eachEntity.setChatMessage("[视频]");
					break;
				case MDatabaseConstants.MESSAGE_TYPE_LOCATION:
					eachEntity.setChatMessage(j.getMessageText());
			}
			chatRowList.add(eachEntity);
			Log.d("eachEntity", eachEntity.toString());
		}
	}

}
