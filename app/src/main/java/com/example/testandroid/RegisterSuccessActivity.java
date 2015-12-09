package com.example.testandroid;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dao.MDatabaseConstants;
import com.example.entity.ChatMsgEntity;
import com.example.entity.Constants;
import com.example.entity.MMessage;
import com.example.thread.ThreadManager;
import com.example.util.MDataOperation;
import com.example.util.MFileUtil;
import com.example.util.MMicroUtil;
import com.example.util.MSharedPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterSuccessActivity extends Activity implements OnClickListener {
	private Location currentLocation;
	private String talkerName="aaron", talkerID="aaron";
	private Button mBtnSend;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private Context context;
	private List<ChatMsgEntity> mDataArrays=new ArrayList<ChatMsgEntity>();
	private final static int COUNT=1;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
		 //预先设置允许改变的窗口状态，需在 setContentView 之前调用，否则设置标题时抛运行时错误。
		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		 setContentView(R.layout.login_success);
		 context=this;
		 getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		 initView();
		 ThreadManager.getInstance().startGetMessageThread(context);
	    }
	public void initView(){
		mListView= (ListView) findViewById(R.id.listview);
		mBtnSend= (Button) findViewById(R.id.btn_send);
		mEditTextContent=(EditText)findViewById(R.id.et_sendmessage);
		initData();
		mBtnSend.setOnClickListener(this);
		mListView.setSelection(mAdapter.getCount() - 1);
	}
	public void initData(){
		for(int i=0;i<COUNT;i++){
			ChatMsgEntity entity =new ChatMsgEntity();
			entity.setDate("2015-12-2 18:00:00");
			if(i%2==0){
				entity.setName("小熊");
				entity.setMsgType(true);// 收到的消息
			} else {
				entity.setName("兔子");
				entity.setMsgType(false);// 自己发送的消息
			}
			entity.setMessage("你好");
			mDataArrays.add(entity);
		}
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_send:
				send();
				break;
		}
	}
	private void send(){
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			// 将消息写入数据库
			writeMessage(MDatabaseConstants.MESSAGE_TYPE_TEXT, contString, 0);
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("兔子");
			entity.setDate(getDate());
			entity.setMessage(contString);
			entity.setMsgType(false);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
			mEditTextContent.setText("");// 清空编辑框数据
			mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
		}
	}
	private String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		return format.format(new Date());
	}
	private void writeMessage(int messageType, String text, int fileDuration) {
		//手动填写发送人信息
//		String userID="kathy";
//		String userName="kathy";
		String userID = MSharedPreference.get(this, MSharedPreference.USER_ID, "");
		String userName = MSharedPreference.get(this, MSharedPreference.USER_NAME, "");
		// int talkerID = Integer.parseInt(this.talkerID);
		String time = MMicroUtil.getCurrentTimeStamp();
		String id = MMicroUtil.getCurrentTimeMillis();
		String sendPosition = "";
		if (currentLocation != null) {
			sendPosition = "(" + currentLocation.getLatitude() + ","
					+ currentLocation.getLongitude() + ")";
			// sendPosition =
			// MJsonParseUtil.getSendPositionJson(currentLocation,
			// userID, userName, time);
		}
		// 需要写入数据库的消息
		MMessage message;
		if (messageType == MDatabaseConstants.MESSAGE_TYPE_TEXT
				|| messageType == MDatabaseConstants.MESSAGE_TYPE_LOCATION
				|| messageType == MDatabaseConstants.MESSAGE_TYPE_TRACK
				|| messageType == MDatabaseConstants.MESSAGE_TYPE_QRCODE) {
			// 添加文本消息
			message = new MMessage(id, userID, userName, talkerID, talkerName,
					text, null, 0L, 0, messageType, time, sendPosition);
			Log.d("message",message.toString());
		} else {
			Long fileSize = MFileUtil.getFileSize(text);
			// 添加文件消息
			message = new MMessage(id, userID, userName, talkerID, talkerName,
					null, text, fileSize, fileDuration, messageType, time,
					sendPosition);
		}
		// 将消息写入数据库
		ArrayList<MMessage> messageList = new ArrayList<MMessage>(1);
		messageList.add(message);
		MDataOperation.writeMessages(messageList,
				Constants.DATA_STORE_DATABASE, true, this);
		// 发送消息
		sendMMessage(message);
	}
	/**
	 * 将消息发送给服务器
	 *
	 * @param message
	 * 要发送的消息
	 */
	private void sendMMessage(MMessage message) {
		// 开启发送消息的线程
		ThreadManager.getInstance().startSendMessageThread(message);
	}
}
