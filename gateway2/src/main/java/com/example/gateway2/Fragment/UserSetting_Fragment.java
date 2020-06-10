package com.example.gateway2.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gateway2.ChangePwd;
import com.example.gateway2.Login;
import com.example.gateway2.R;


public class UserSetting_Fragment extends Fragment {
    private Button logout_btn;
    private Button changePwd_btn;
    private TextView username_text;

    private String username;
    private String passwd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usersetting_fragment,null);


//        获取Activity中的数据
        Bundle bundle = getActivity().getIntent().getExtras();
        username = getData(bundle,"username");
        passwd = getData(bundle,"passwd");

//        username_text = getActivity().findViewById(R.id.username);
//        username_text.setText(username);

        return view;
    }
//事件响应必须在onActivityCreated方法中
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logout_btn = getActivity().findViewById(R.id.logout_btn);
        changePwd_btn = getActivity().findViewById(R.id.changepwd_btn);
        username_text = getActivity().findViewById(R.id.username);
        username_text.setText(username);

        logout_btn.setOnClickListener(l);
        changePwd_btn.setOnClickListener(l);

    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.logout_btn:
//                    注销登录
                    logout();
                    break;
                case R.id.changepwd_btn:
//                    修改密码
                    changepwd();
                    Log.i("LYC","jumpToChangepwd4");
                    break;
            }
        }
    };
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }
    private void logout(){
        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void changepwd(){
//        Intent intent = new Intent(getActivity(),ChangePwd.class);
        Intent intent = new Intent(getActivity(), ChangePwd.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        intent.putExtras(bundle);
        startActivity(intent);

        Log.i("LYC","jumpToChangepwd3");
    }
}
