package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.entity.ChatMsgEntity;
import com.example.entity.Contact;
import com.example.entity.ContactRow;
import com.example.testandroid.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by kathy on 2015/12/28.
 */
public class ContactAdapter extends BaseAdapter {
    private List<ContactRow> coll;//消息对象数组
    private Context context;
    private LayoutInflater mInflater;
    public ContactAdapter(Context context, List<ContactRow> coll){
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
        final ContactRow entity=coll.get(position);
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.row_contact,null);
            viewHolder=new ViewHolder();
            viewHolder.userName=(TextView)convertView.findViewById(R.id.contactName);
            viewHolder.userHead=(ImageView)convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        String contactName=entity.getTitle();
        Integer contactID=entity.getIcon();
        viewHolder.userName.setText(contactName);
        viewHolder.userHead.setImageResource(contactID);
        return convertView;
    }
    static class ViewHolder{
        //消息发送者
        public TextView userName;
        public ImageView userHead;
    }
}
