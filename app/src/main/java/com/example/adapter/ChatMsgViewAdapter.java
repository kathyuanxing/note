package com.example.adapter;


import java.lang.ref.SoftReference;
import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.util.Log;
import android.widget.BaseAdapter;

import com.example.dao.MDatabaseConstants;
import com.example.entity.ChatMsgEntity;
import com.example.testandroid.R;
import com.example.util.MImageUtil;

import java.util.List;

/**
 * Created by kathy on 2015/12/2.
 */
public class ChatMsgViewAdapter extends BaseAdapter {
    public static final int RIGHT_ITEM = 0;
    public static final int LEFT_ITEM = 1;
    // 音频播放动画
    private AnimationDrawable audioPlayingAnimation;
    // 音频播放类
    private MediaPlayer mMediaPlayer;
    // 应用上下文
    protected Context context;
    // ListView元素的资源标识
    protected int mRLayoutList;
    // 软引用
    private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
    private List<ChatMsgEntity> coll;//消息对象数组
    private LayoutInflater mInflater;
    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll){
        this.context=context;
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
        final ViewHolder viewHolder;
        Log.d("position",position+"");
        ChatMsgEntity entity=coll.get(position);
        boolean isComMsg=entity.getMsgType();
        if(convertView==null){
            if(isComMsg){
                convertView=mInflater.inflate(R.layout.left,null);
            }else {
                convertView=mInflater.inflate(R.layout.right,null);
            }
            viewHolder=new ViewHolder();
            viewHolder.tvSendTime=(TextView)convertView.findViewById(R.id.tv_sendtime);
            viewHolder.content = (RelativeLayout) convertView.findViewById(R.id.message_content);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.media=(ImageView)convertView.findViewById(R.id.message_media_imageView);
            viewHolder.location=(ImageView)convertView.findViewById(R.id.message_location_imageView);
            viewHolder.isComMsg = isComMsg;
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        // 文件路径
        final String path = ((ChatMsgEntity)coll.get(position)).getPath();
        // 消息类型
        int type = ((ChatMsgEntity)coll.get(position)).getType();
        // 缩略图
        Bitmap thumbnail = null;
        switch (type) {
            case MDatabaseConstants.MESSAGE_TYPE_TEXT:
                // 文本消息
                String text = ((ChatMsgEntity)coll.get(position)).getMessage();
                // 显示文本
                viewHolder.tvContent.setVisibility(View.VISIBLE);
                viewHolder.media.setVisibility(View.GONE);
                viewHolder.location.setVisibility(View.GONE);

                viewHolder.tvContent.setText(entity.getMessage());
                break;

            case MDatabaseConstants.MESSAGE_TYPE_IMAGE:
                // 显示图片
                viewHolder.tvContent.setVisibility(View.GONE);
                viewHolder.media.setVisibility(View.VISIBLE);
                viewHolder.location.setVisibility(View.GONE);

                // 如果在内存中有缓存，直接调用
                if (imageCache.containsKey(path)) {
                    SoftReference<Bitmap> softReference = imageCache.get(path);
                    Bitmap bitmap = softReference.get();
                    if (bitmap != null) {
                        thumbnail = bitmap;
                    }
                }// 内存中无缓存，生成图片
                else {
                    // 生成缩略图
                    thumbnail = MImageUtil.getThumbnail(path, 120, 120, context);
                    // 添加入图片缓存，便于回收
                    if (thumbnail != null) {
                        imageCache.put(path, new SoftReference<Bitmap>(thumbnail));
                    }
                }
                // 显示图片
                if (thumbnail != null) {
                    viewHolder.media.setImageBitmap(thumbnail);
                }
                break;
            case MDatabaseConstants.MESSAGE_TYPE_VIDEO:
                // 显示视频
                viewHolder.tvContent.setVisibility(View.GONE);
                viewHolder.media.setVisibility(View.VISIBLE);
                viewHolder.location.setVisibility(View.GONE);

                // 如果在内存中有缓存，直接调用
                if (imageCache.containsKey(path)) {
                    SoftReference<Bitmap> softReference = imageCache.get(path);
                    Bitmap bitmap = softReference.get();
                    if (bitmap != null) {
                        thumbnail = bitmap;
                    }
                }// 内存中无缓存，生成图片
                else {
                    // 生成缩略图
                    thumbnail = ThumbnailUtils.createVideoThumbnail(path,
                            Thumbnails.MICRO_KIND);
                    // 为缩略图添加水印
                    thumbnail = MImageUtil.drawWatermark(thumbnail,
                            R.drawable.play, context);
                    // 添加入图片缓存，便于回收
                    if (thumbnail != null) {
                        imageCache.put(path, new SoftReference<Bitmap>(thumbnail));
                    }
                }

                // 添加点击事件
                viewHolder.content.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 点击播放视频
                        Intent mIntent = new Intent(Intent.ACTION_VIEW);
                        mIntent.setDataAndType(Uri.parse("file://" + path),
                                "video/mp4");
                        context.startActivity(mIntent);
                    }
                });
                // 显示图片
                if (thumbnail != null)
                    viewHolder.media.setImageBitmap(thumbnail);
                break;
            case MDatabaseConstants.MESSAGE_TYPE_AUDIO:
                // 显示音频
                viewHolder.tvContent.setVisibility(View.GONE);
                viewHolder.media.setVisibility(View.VISIBLE);
                viewHolder.location.setVisibility(View.GONE);

                // 根据发送人判断放置的音频图标
                boolean isSendByUser = getItemViewType(position) == RIGHT_ITEM ? true : false;
                viewHolder.media.setImageResource(isSendByUser ? R.drawable.audio_playing_right : R.drawable.audio_playing_left);




                // 不知道为什么音频动画会自动播放，只能手动阻止（无力吐槽。。。）
                audioPlayingAnimation = (AnimationDrawable) viewHolder.media
                        .getDrawable();
                audioPlayingAnimation.stop();

                // 添加点击事件
               viewHolder.content.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 播放音频
                        playAudio(path);
                        // 停止先前动画并重置
                        if (audioPlayingAnimation != null) {
                            audioPlayingAnimation.stop();
                            audioPlayingAnimation.selectDrawable(0);
                        }
                        // 音频播放动画
                        audioPlayingAnimation = (AnimationDrawable) viewHolder.media.getDrawable();
                        audioPlayingAnimation.start();
                    }
                });
                break;

            case MDatabaseConstants.MESSAGE_TYPE_QRCODE:
                // 显示二维码图片
                break;

            case MDatabaseConstants.MESSAGE_TYPE_LOCATION:
                //显示位置
                break;

            case MDatabaseConstants.MESSAGE_TYPE_TRACK:
                //显示轨迹
                break;
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        return convertView;
    }
    /**
     * 播放音频文件
     *
     * @param path
     *            音频文件路径
     */
    private void playAudio(String path) {
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            // 播放
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            // 播放完成后动画重置
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    audioPlayingAnimation.stop();
                    audioPlayingAnimation.selectDrawable(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放音频
     */
    public void stopPlayAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
        mMediaPlayer = null;
    }
    static class ViewHolder{
        //消息发送时间
        public TextView tvSendTime;
        //消息发送者
        public TextView tvUserName;
        // 消息内容
        public RelativeLayout content;
        //消息文字内容
        public TextView tvContent;
        // 消息媒体文件
        public ImageView media;
        // 消息位置图片
        public ImageView location;
        public boolean isComMsg=true;
    }
}
