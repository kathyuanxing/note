package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.ChatMsgEntity;
import com.example.testandroid.R;

import java.util.List;

/**
 * Created by kathy on 2015/12/2.
 */
public class ChatMsgViewAdapter extends BaseAdapter {
    private List<ChatMsgEntity> coll;//消息对象数组
    private LayoutInflater mInflater;
    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll){
        this.coll=coll;
        mInflater=LayoutInflater.from(context);
    }
    public int getItemViewType(int position){
        return coll.get(position).getMsgType()?1:0;
    }
    public int getViewTypeCount(){
        return 2;
    }
    public int getCount(){
        return coll.size();
    }
    public long getItemId(int positon){
        return positon;
    }
    public Object getItem(int positon){
        return coll.get(positon);
    }
    public View getView(int position,View convertView,ViewGroup parent){
        //position从上至下升序排列
        Log.d("position",position+"");
        ChatMsgEntity entity=coll.get(position);
        boolean isComMsg=entity.getMsgType();
        ViewHolder viewHolder=null;
        if(convertView==null){
            if(isComMsg){
                convertView=mInflater.inflate(R.layout.activity_imitate_weixin_chatting_item_msg_text_left,null);
            }else {
                convertView=mInflater.inflate(R.layout.activity_imitate_weixin_chatting_item_msg_text_right,null);
            }
            viewHolder=new ViewHolder();
            viewHolder.tvSendTime=(TextView)convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getMessage());
        return convertView;
    }
    static class ViewHolder{
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg=true;
    }
}
