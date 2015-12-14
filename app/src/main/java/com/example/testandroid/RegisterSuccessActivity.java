package com.example.testandroid;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dao.MDatabaseConstants;
import com.example.adapter.ChatMsgViewAdapter;
import com.example.adapter.ExpressionAdapter;
import com.example.adapter.ExpressionPagerAdapter;
import com.example.entity.ChatMsgEntity;
import com.example.entity.Constants;
import com.example.entity.MMessage;
import com.example.thread.ThreadManager;
import com.example.util.MDataOperation;
import com.example.util.MFileUtil;
import com.example.util.MImageUtil;
import com.example.util.MMicroUtil;
import com.example.util.MSharedPreference;
import com.example.widget.ExpandGridView;
import com.example.util.SmileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class RegisterSuccessActivity extends Activity implements OnClickListener {
	private Location currentLocation;
	private LinearLayout attachmentRelativeLayout;// 添加附件的布局
	private String talkerName="aaron", talkerID="aaron";
	private Button mBtnSend;
	private EditText mEditTextContent;
	private ViewPager expressionViewpager;
	private List<String> reslist;
	private LinearLayout emojiIconContainer;
	private View more;
	private InputMethodManager manager;
	private ListView mListView;
	private ImageView iv_emoticons_normal;
	private ImageView iv_emoticons_checked;
	private ChatMsgViewAdapter mAdapter;
	private Button btnMore;
	public String imageUri = "";
	public String videoUri = "";
	public String currentPhotoPath = "";
	private View buttonSetModeKeyboard;
	private Context context;
	private LinearLayout btnContainer;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CODE_SELECT_VIDEO = 23;
	private int currentCount = 0; // 当前显示的条目数
	private List<ChatMsgEntity> entityList=new ArrayList<ChatMsgEntity>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
		 //预先设置允许改变的窗口状态，需在 setContentView 之前调用，否则设置标题时抛运行时错误。
//		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_chat);
		 context=this;
//		 getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		 initView();
		 manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 ThreadManager.getInstance().startGetMessageThread(context);
	    }
	public void initView(){
		attachmentRelativeLayout = (LinearLayout) findViewById(R.id.ll_btn_container);
		mListView= (ListView) findViewById(R.id.listview);
		mBtnSend= (Button) findViewById(R.id.btn_send);
		btnMore = (Button) findViewById(R.id.btn_more);
		mEditTextContent=(EditText)findViewById(R.id.et_sendmessage);
		more = findViewById(R.id.more);
		emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
		iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
		iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
		emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
		btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
		expressionViewpager = (ViewPager) findViewById(R.id.vPager);
		buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
		initData();
		mBtnSend.setOnClickListener(this);
		mListView.setSelection(mAdapter.getCount() - 1);
		// 表情list
		reslist = getExpressionRes(35);
		// 初始化表情viewpager
		List<View> views = new ArrayList<View>();
		View gv1 = getGridChildView(1);
		View gv2 = getGridChildView(2);
		views.add(gv1);
		views.add(gv2);
		expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
		mEditTextContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					btnMore.setVisibility(View.GONE);
					mBtnSend.setVisibility(View.VISIBLE);
				} else {
					btnMore.setVisibility(View.VISIBLE);
					mBtnSend.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	public void initData(){
		String userID = MSharedPreference.get(this, MSharedPreference.USER_ID, "");
		ArrayList<MMessage> messageList = MDataOperation.readReverseMessages(
				currentCount, Constants.DATA_STORE_DATABASE, userID, talkerID,
				this);
		for (MMessage j : messageList) {
			//遍历MMessage
			boolean flagType;
			ChatMsgEntity eachEntity =new ChatMsgEntity();
			eachEntity.setName(j.getSenderName());
			if(j.getSenderName().equals(userID)){
				flagType=false;
			}else {
				flagType=true;
			}
			eachEntity.setPath(j.getFilePath());
			eachEntity.setType(j.getType());
			eachEntity.setDate(j.getTime());
			eachEntity.setMessage(j.getMessageText());
			eachEntity.setMsgType(flagType);
			entityList.add(eachEntity);
			Log.d("messagelist",j.toString());
		}
		Log.d("messageList", entityList.toString());

		mAdapter = new ChatMsgViewAdapter(this, entityList);
		mListView.setAdapter(mAdapter);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_send:
				//点击发送按钮
				send();
				break;
			case R.id.iv_emoticons_normal:
				 // 点击显示表情框
				more.setVisibility(View.VISIBLE);
				iv_emoticons_normal.setVisibility(View.INVISIBLE);
				iv_emoticons_checked.setVisibility(View.VISIBLE);
				btnContainer.setVisibility(View.GONE);
				emojiIconContainer.setVisibility(View.VISIBLE);
				hideKeyboard();
				break;
			case  R.id.iv_emoticons_checked:
				// 点击隐藏表情框
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				btnContainer.setVisibility(View.VISIBLE);
				emojiIconContainer.setVisibility(View.GONE);
				more.setVisibility(View.GONE);
				break;
			case R.id.btn_take_picture:
				// 点击照相图标
				selectPicFromCamera();// 点击照相图标
				break;
			case R.id.btn_picture:
				// 点击图片图标
				selectPicFromLocal();
				break;
				// 点击位置图标
			case R.id.btn_location:
//				startActivityForResult(new Intent(this, BaiduMapActivity.class), REQUEST_CODE_MAP);
				break;
			case R.id.btn_video:
				// 点击摄像图标
				selectVideoFromCamera();
		}
	}
	public void editClick(View v) {
		mListView.setSelection(mListView.getCount() - 1);
		if (more.getVisibility() == View.VISIBLE) {
			more.setVisibility(View.GONE);
			iv_emoticons_normal.setVisibility(View.VISIBLE);
			iv_emoticons_checked.setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 显示或隐藏图标按钮页
	 *
	 * @param view
	 */
	public void more(View view) {
		if (more.getVisibility() == View.GONE) {
			System.out.println("more gone");
			hideKeyboard();
			more.setVisibility(View.VISIBLE);
			btnContainer.setVisibility(View.VISIBLE);
			emojiIconContainer.setVisibility(View.GONE);
		} else {
			if (emojiIconContainer.getVisibility() == View.VISIBLE) {
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.VISIBLE);
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
			} else {
				more.setVisibility(View.GONE);
			}

		}

	}
	private void send(){
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			// 将消息写入数据库
			writeMessage(MDatabaseConstants.MESSAGE_TYPE_TEXT, contString, 0);
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("kathy");
			entity.setDate(getDate());
			entity.setMessage(contString);
			entity.setType(MDatabaseConstants.MESSAGE_TYPE_TEXT);
			entity.setMsgType(false);

			entityList.add(entity);
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
	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public List<String> getExpressionRes(int getSum) {
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;

			reslist.add(filename);

		}
		return reslist;

	}
	/**
	 * 获取表情的gridview的子view
	 *
	 * @param i
	 * @return
	 */
	private View getGridChildView(int i) {
		View view = View.inflate(this, R.layout.expression_gridview, null);
		ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();
		if (i == 1) {
			List<String> list1 = reslist.subList(0, 20);
			list.addAll(list1);
		} else if (i == 2) {
			list.addAll(reslist.subList(20, reslist.size()));
		}
		list.add("delete_expression");
		final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this,
				1, list);
		gv.setAdapter(expressionAdapter);
		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				String filename = expressionAdapter.getItem(position);
				try {
					// 文字输入框可见时，才可输入表情
					// 按住说话可见，不让输入表情
					if (buttonSetModeKeyboard.getVisibility() != View.VISIBLE) {

						if (filename != "delete_expression") { // 不是删除键，显示表情
							// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
							@SuppressWarnings("rawtypes")
							Class clz = Class.forName("com.example.util.SmileUtils");
							Field field = clz.getField(filename);
							mEditTextContent.append(SmileUtils.getSmiledText(
									RegisterSuccessActivity.this, (String) field.get(null)));
						} else { // 删除文字或者表情
							if (!TextUtils.isEmpty(mEditTextContent.getText())) {

								int selectionStart = mEditTextContent
										.getSelectionStart();// 获取光标的位置
								if (selectionStart > 0) {
									String body = mEditTextContent.getText()
											.toString();
									String tempStr = body.substring(0,
											selectionStart);
									int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
									if (i != -1) {
										CharSequence cs = tempStr.substring(i,
												selectionStart);
										if (SmileUtils.containsKey(cs
												.toString()))
											mEditTextContent.getEditableText()
													.delete(i, selectionStart);
										else
											mEditTextContent.getEditableText()
													.delete(selectionStart - 1,
															selectionStart);
									} else {
										mEditTextContent.getEditableText()
												.delete(selectionStart - 1,
														selectionStart);
									}
								}
							}

						}
					}
				} catch (Exception e) {
				}

			}
		});
		return view;
	}
	/**
	 * 从图库获取图片
	 */
	public void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}
	/**
	 * 照相获取图片
	 */
	public void selectPicFromCamera() {
		Intent intent=new Intent();
		        	 // 指定开启系统相机的Action
		             intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		             intent.addCategory(Intent.CATEGORY_DEFAULT);
		imageUri = MFileUtil.getMediaUri(Constants.ATTACHMENT_TYPE_IMAGE);
		           	// 根据文件地址创建文件
		            File file=new File(imageUri);
		            // 把文件地址转换成Uri格式
		            Uri uri=Uri.fromFile(file);
		         	// 设置系统相机拍摄照片完成后图片文件的存放地址
		            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					currentPhotoPath = file.getAbsolutePath();
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}
	public void selectVideoFromCamera(){
		Intent intent = new Intent();
		             intent.setAction("android.media.action.VIDEO_CAPTURE");
		intent.addCategory("android.intent.category.DEFAULT");
		videoUri = MFileUtil.getMediaUri(Constants.ATTACHMENT_TYPE_VIDEO);
		             File file = new File(videoUri);
		           	 Uri uri = Uri.fromFile(file);
		             intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		             startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 媒体文件路径
		String tempUri;
		String mediaUri;
		if (requestCode == REQUEST_CODE_CAMERA
				&& resultCode == Activity.RESULT_OK) {
			Log.v("ANDROIDDEBUG", currentPhotoPath);
			// 隐藏添加附件的布局
			attachmentRelativeLayout.setVisibility(View.GONE);
			// 获取附件路径
//			mediaUri = data.getExtras().getString("uri");
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("kathy");
			entity.setDate(getDate());
			entity.setPath(currentPhotoPath);
			entity.setType(MDatabaseConstants.MESSAGE_TYPE_IMAGE);
			entity.setMsgType(false);
			/*将所得照片压缩，并将原图删除*/
			// 缩略图路径
			tempUri = MFileUtil.getMediaUri(Constants.ATTACHMENT_TYPE_IMAGE);
			// 生成缩略图
			MImageUtil.getThumbnail(currentPhotoPath, tempUri);
			// 删除原图
			MFileUtil.deleteFile(currentPhotoPath);
			currentPhotoPath=tempUri;
			// 将消息写入数据库
			writeMessage(MDatabaseConstants.MESSAGE_TYPE_IMAGE, currentPhotoPath, 0);
			entityList.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
			mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
		} else if (requestCode == REQUEST_CODE_SELECT_VIDEO
				&& resultCode == Activity.RESULT_OK) {
			// 附件时长
			int duration = data.getExtras().getInt("Duration");
			if (duration > 30) {
				Toast.makeText(this, "视频文件过大！", Toast.LENGTH_SHORT).show();
				return;
			}
			// 获取附件路径
			mediaUri = data.getExtras().getString("uri");
			// 将消息写入数据库
			writeMessage(MDatabaseConstants.MESSAGE_TYPE_VIDEO, mediaUri,
					duration);
		}
	}

}
