package com.example.testandroid;

import android.app.Activity;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.example.util.HttpUtil;
import android.content.SharedPreferences.Editor;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class MainActivity extends Activity {
    private static final int MSG_SUCCESS = 0;
    private static final int MSG_FAILURE = 1;
    Button loginBtn;
    EditText et_username;
    EditText et_password;
    CheckBox auto_remember;
    CheckBox auto_login;
    private SharedPreferences sp;
    int flag=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auto_remember=(CheckBox)findViewById(R.id.auto_save_password);
        auto_login=(CheckBox)findViewById(R.id.auto_login);
        et_username = (EditText)findViewById(R.id.et_user);
        et_password =( EditText)findViewById(R.id.et_psw);
        loginBtn = (Button)findViewById(R.id.btn_login);
        sp=this.getSharedPreferences("remember", this.MODE_PRIVATE);
        //判断记住密码多选框的状态，如果不存在ISCHECK，返回false
        if(sp.getBoolean("ISCHECK", false)){
            //设置CheckBox是记录密码状态
            auto_remember.setChecked(true);
            et_username.setText(sp.getString("username", ""));
            et_password.setText(sp.getString("password",""));
            if(sp.getBoolean("AUTO_ISCHECK", false)){
                //设置CheckBox自动登陆状态
                auto_login.setChecked(true);
                Intent intent=new Intent(MainActivity.this,RegisterSuccessActivity.class);
                startActivity(intent);
            }
        }
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String un=et_username.getText().toString().trim();
                final String ps=et_password.getText().toString().trim();
                final HashMap data=new HashMap();
                data.put("userName", un);
                data.put("passWord", ps);
                Thread thread = new Thread(){
                    public void run(){
                        try {
                            flag=getResult(un, ps);
                        } catch (URISyntaxException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Message msg = messageHandler.obtainMessage();
                        msg.what=flag;
                        msg.obj=data;
                        System.out.println(flag);
                        messageHandler.sendMessage(msg);
                    }
                };
                thread.start();//线程启动
            }
        });
        auto_remember.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_remember.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }
    private Handler messageHandler =new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_SUCCESS:
                    if(auto_remember.isChecked()){
                        Editor editor=sp.edit();
                        HashMap data=new HashMap();
                        data=(HashMap) msg.obj;
                        String un_copy=(String) data.get("userName");
                        String ps_copy=(String)data.get("passWord");
                        editor.putString("username", un_copy);
                        editor.putString("password",ps_copy);
                        editor.commit();
                    }
                    Intent nextIntent = new Intent(MainActivity.this, RegisterSuccessActivity.class);
                    startActivity(nextIntent);
                    // 结束该Activity
                    finish();
                    break;
                case MSG_FAILURE:
                    //	Toast.makeText(getApplicationContext(), "retry", Toast.LENGTH_SHORT);
                    break;
            }
        }
    };
    private Integer getResult(String username,String password) throws URISyntaxException{
        String strFlag="";
        String flagResult="";
        String originurl = "un=" + username + "&pw=" + password;  //GET方式
        final URI uri = new URI("http", "172.16.134.14:8080", "/MyTest/login",originurl, null);
        try {
            strFlag = HttpUtil.getRequest(uri);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(strFlag.equals("true")){
            return 0;
        }else{
            return 1;
        }
    }
}