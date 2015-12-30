package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.ChatRow;
import com.example.entity.ContactRow;
import com.example.testandroid.R;

import java.util.List;

/**
 * Created by kathy on 2015/12/29.
 * 聊天记录
 */
public class HistoryAdapter  extends BaseAdapter {
    private List<ChatRow> coll;//消息对象数组
    private Context context;
    private LayoutInflater mInflater;
    public HistoryAdapter(Context context, List<ChatRow> coll){
        this.context=context;
        this.coll=coll;
        mInflater= LayoutInflater.from(context);
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
    public View getView(int position,View convertView,ViewGroup parent) {
        //position从上至下升序排列
        final ViewHolder viewHolder;
        String name,time,message;
        Integer head,read;
        final ChatRow entity=coll.get(position);
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.row_chat_history,null);
            viewHolder=new ViewHolder();
            viewHolder.userName=(TextView)convertView.findViewById(R.id.name_friend);
            viewHolder.time=(TextView)convertView.findViewById(R.id.time_friend);
//            viewHolder.unRead=(TextView)convertView.findViewById(R.id.unread_msg_number);
            viewHolder.userMessage=(TextView)convertView.findViewById(R.id.message);
            viewHolder.userHead=(ImageView)convertView.findViewById(R.id.avatar_friend);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        name=entity.getChatName();
        time=entity.getChatTime();
//        read=entity.getChatState();
        message=entity.getChatMessage();
        head=entity.getChatHead();
        viewHolder.userName.setText(name);
        viewHolder.time.setText(time);
//        viewHolder.unRead.setText(read);
        viewHolder.userMessage.setText(message);
        viewHolder.userHead.setImageResource(head);
        return convertView;
    }

    static class ViewHolder{
        //聊天记录样式定义
        public TextView userName;
        public ImageView userHead;
        public TextView userMessage;
//        public TextView unRead;
        public TextView time;

    }
}
