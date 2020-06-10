package com.example.gateway2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gateway2.Fragment.Device_Fragment;
import com.example.gateway2.Fragment.Gateway_Fragment;
import com.example.gateway2.Fragment.UserSetting_Fragment;

public class User extends AppCompatActivity {
    private String usernmae;
    private String passwd;
    private Fragment fg_user,fg_device,fg_gateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        Bundle bundle = this.getIntent().getExtras();
        //登录成功，获取用户数据
        //调用函数
        usernmae = getData(bundle,"username");
        passwd = getData(bundle,"passwd");

//        Log.i("LYC","why");
//        TextView userName = findViewById(R.id.UserName);
//        userName.setText(usernmae);
//切换界面按钮
        Button gateway_btn = (Button) findViewById(R.id.gatewaysetting);
        Button device_btn = (Button) findViewById(R.id.devicesetting);
        Button userseting_btn = (Button) findViewById(R.id.usersetting);

        gateway_btn.setOnClickListener(l);
        device_btn.setOnClickListener(l);
        userseting_btn.setOnClickListener(l);

        showUserFragment();
    }
// 展示最初的用户界面
    private void showUserFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
        if(fg_user == null) {
            fg_user = new UserSetting_Fragment();
            transaction.add(R.id.frag_container,fg_user);
            Bundle bundle = new Bundle();
            bundle.putString("username",usernmae);
            bundle.putString("passwd",passwd);
            fg_user.setArguments(bundle);
        }
        transaction.commit();
    }
//    获取数据
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }
//隐藏其他fragment
    private void hideAllFragment(FragmentTransaction transaction){
        if (fg_user != null){
            transaction.hide(fg_user);
        }
        if (fg_device != null) {
            transaction.hide(fg_device);
        }
        if(fg_gateway != null) {
            transaction.hide(fg_gateway);
        }
    }
//    button响应
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            switch (view.getId()){
                case R.id.gatewaysetting:
                    if (fg_gateway == null) {
                        fg_gateway = new Gateway_Fragment();
                        transaction.add(R.id.frag_container,fg_gateway);
                    }
                    else {
                        transaction.show(fg_gateway);
                    }
//                    f = new Gateway_Fragment();
                    break;
                case R.id.devicesetting:
                    if (fg_device == null) {
                        fg_device = new Device_Fragment();
                        transaction.add(R.id.frag_container,fg_device);
                    }
                    else {
                        transaction.show(fg_device);
                    }
//                    f = new Device_Fragment();
                    break;
                case R.id.usersetting:
                    if (fg_user == null) {
                        fg_user = new UserSetting_Fragment();
                        transaction.add(R.id.frag_container,fg_user);
                    }
                    else {
                        transaction.show(fg_user);
//                        transaction.hide(gateway);
                    }
//                    f = new UserSetting_Fragment();
                    break;
            }
//            ft.replace(R.id.fragment, f);
            transaction.commit();
        }

    };
//    展示UserFragment
//    private void showUserFragment(){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if(fg_user == null) {
//            fg_user = UserSetting.newInstance(usernmae,passwd);
//            transaction.add(R.id.frag_container,fg_user);
//        }
//
//        Log.i("LYC","wrnm");
//        hideAllFragment();
//        Log.i("LYC","wrnm");
//        transaction.show(fg_user);
//        Log.i("LYC","wrnm1");
//        transaction.commit();
//        Log.i("LYC","wrnm1");
//    }
//    展示DeviceFragment
//    private void showDeviceFragment(){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if(fg_device == null) {
//            fg_device = ShowDevice.newInstance(usernmae,passwd);
////            fg_device = new
//            transaction.add(R.id.frag_container,fg_device);
//        }
//        hideAllFragment();
//        transaction.show(fg_device);
//        transaction.commit();
//    }
//    private void showGatewayFragment(){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if(fg_gateway == null) {
//            fg_gateway = Gateway.newInstance(usernmae,passwd);
////            fg_gateway = new Gateway();
//            transaction.add(R.id.frag_container,fg_gateway);
//        }
//        hideAllFragment();
//        transaction.show(fg_gateway);
//        transaction.commit();
//    }
//隐藏所有Fragment
//    private void hideAllFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if(fg_user != null){
//            transaction.hide(fg_user);
//        }
//        if (fg_device != null){
//            transaction.hide(fg_device);
//        }
//        if(fg_gateway != null){
//            transaction.hide(fg_gateway);
//        }
//        transaction.commit();
//    }



//    View.OnClickListener l = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.usersetting:
//                    showUserFragment();
//                case R.id.devicesetting:
//                    showDeviceFragment();
//                case R.id.gatewaysetting:
//                    showGatewayFragment();
//            }
//        }
//    };

//    View.OnClickListener l = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View view) {
////            FragmentManager fm = getFragmentManager();
////            Fragment f = null;
////            FragmentTransaction ft = fm.beginTransaction();
////            这行具体什么意思？
////            FragmentTransaction transaction = getFragmentManager().beginTransaction();
////            hideAllFragment(transaction);
//            switch (view.getId()){
//                case R.id.gatewaysetting:
//                    if (gateway == null) {
//                        gateway = new Gateway_Fragment();
//                        transaction.add(R.id.fragment,gateway);
//                    }
//                    else {
//                        transaction.show(gateway);
//                    }
////                    f = new Gateway_Fragment();
//                    break;
//                case R.id.devicesetting:
//                    if (device == null) {
//                        device = new Device_Fragment();
//                        transaction.add(R.id.fragment,device);
//                    }
//                    else {
//                        transaction.show(device);
//                    }
////                    f = new Device_Fragment();
//                    break;
//                case R.id.usersetting:
//                    if (user == null) {
//                        user = new UserSetting_Fragment();
//                        transaction.add(R.id.fragment,user);
//                    }
//                    else {
//                        transaction.show(user);
////                        transaction.hide(gateway);
//                    }
////                    f = new UserSetting_Fragment();
//                    break;
//            }
////            ft.replace(R.id.fragment, f);
//            transaction.commit();
//        }
//    };
}
