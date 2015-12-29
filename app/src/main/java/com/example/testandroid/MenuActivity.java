package com.example.testandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.fragment.MyFragment1;
import com.example.fragment.MyFragment2;
import com.example.fragment.MyFragment3;
import com.example.fragment.MyFragment4;
import com.example.thread.ReadContactThread;
import com.example.thread.ThreadManager;

/**
 * Created by kathy on 2015/12/25.
 */
public class MenuActivity  extends FragmentActivity   {
    private TextView txt_title;
    private ImageView img_right;
    private Fragment[] fragments;
    public MyFragment1 homefragment;
    private MyFragment2 contactlistfragment;
    private MyFragment3 findfragment;
    private MyFragment4 profilefragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    public static boolean contactUpdating=false;
    private final static String TAG_CHAT = "chat";
    private static final String TAB_CONTACT = "contact";
    private static final String TAB_DISCOVER = "discover";
    private static final String TAB_ME = "me";
    private FragmentTabHost tabhost;
    private int currentTabIndex;
    private int index;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = this;
        initView();
        // 开启联系人加载线程
        MenuActivity.contactUpdating=true;
        new ReadContactThread(context).start();
        ThreadManager.getInstance().startGetMessageThread(context);
    }

    public void initView() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        img_right = (ImageView) findViewById(R.id.img_right);
        homefragment = new MyFragment1();
        contactlistfragment = new MyFragment2();
        findfragment = new MyFragment3();
        profilefragment = new MyFragment4();
        fragments = new Fragment[] { homefragment, contactlistfragment,
                findfragment, profilefragment };
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);
        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(0xFF45C01A);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, contactlistfragment)
                .add(R.id.fragment_container, profilefragment)
                .add(R.id.fragment_container, findfragment)
                .hide(contactlistfragment).hide(profilefragment)
                .hide(findfragment).show(homefragment).commit();
    }
    public void onTabClicked(View view) {
        img_right.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.re_weixin:
                img_right.setVisibility(View.VISIBLE);
                index = 0;
                txt_title.setText(R.string.app_name);
                img_right.setImageResource(R.drawable.icon_add);
                break;
            case R.id.re_contact_list:
                index = 1;
                txt_title.setText(R.string.contacts);
                img_right.setVisibility(View.VISIBLE);
                img_right.setImageResource(R.drawable.icon_titleaddfriend);
                break;
            case R.id.re_find:
                index = 2;
                txt_title.setText(R.string.discover);
                break;
            case R.id.re_profile:
                index = 3;
                txt_title.setText(R.string.me);
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);

        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }
}
