package com.example.gateway2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Login extends AppCompatActivity {
    protected EditText account;
    protected EditText passwd;
    private SharedPreferences login_sp;
    private CheckBox remember_btn;
    int returnResult;

    //    信息字符串
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = (EditText)findViewById(R.id.account);
        passwd = (EditText) findViewById(R.id.passwd);
        Button login = (Button)findViewById(R.id.login_btn);
        Button register = (Button)findViewById(R.id.register);

        remember_btn = findViewById(R.id.remember_pwd);
        login_sp = getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "");
        String pwd =login_sp.getString("PASSWORD", "");
        boolean reme_pwd = login_sp.getBoolean("remember_btn",false);
        if(reme_pwd){
            account.setText(name);
            passwd.setText(pwd);
            remember_btn.setChecked(true);
        }

        login.setOnClickListener(login_Listener);
        register.setOnClickListener(login_Listener);

    }
    View.OnClickListener login_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_btn:
                    if(isMessageVaild()) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        login();
                    }
                    break;
                case R.id.register:
                    Intent intent_Login_To_Reg = new Intent(Login.this,Register.class);
                    startActivity(intent_Login_To_Reg);
                    break;
            }
        }
    };

    private void login() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                final String username =  account.getText().toString().trim();
                final String password = passwd.getText().toString().trim();
                final SharedPreferences.Editor editor =login_sp.edit();
//                Log.i("LYC",password+"+"+username);
                try {
                    URL obj = new URL("https://lycgg.xyz/gateway/login.php");
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                    String loginStr = "username="+username+"&passwd="+password;
                    //Log.i("test",loginStr);
                    //添加请求头
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(loginStr);
                    wr.close();
                    //读取返回数据
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));//建立缓冲区
                    String inputLine;
                    final StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);//数据写入
                    }
                    in.close();
                    String result = response.toString();

                    try {
                        //获取服务器返回的json数据
                        JSONObject jsonObject  = new JSONObject(result);
                        returnResult = jsonObject.getInt("status");
                    } catch (JSONException e) {
                        Log.e("log_tag", "the Error parsing data "+e.toString());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (returnResult == 1) {
                                //验证成功，自动保存用户名和密码
                                editor.putString("USER_NAME", username);
                                editor.putString("PASSWORD", password);
                                //是否记住密码
                                if(remember_btn.isChecked()){
                                    editor.putBoolean("remember_btn", true);
                                }else{
                                    editor.putBoolean("remember_btn", false);
                                }
                                editor.commit();
                                Toast.makeText(Login.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                //获取用户设备数据
                                getDevice();
                                //页面跳转
                                jumpToUser();
                                //finish();
                            } else {
                                Toast.makeText(Login.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                                //return;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }).start();
    }
    //跳转到用户界面
    private void  jumpToUser(){
        Intent intent = new Intent(Login.this,User.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",account.getText().toString().trim());
        bundle.putString("passwd",passwd.getText().toString().trim());
        intent.putExtras(bundle);
        startActivity(intent);
//        Log.i("LYC","why1");
        finish();
    }
    //判断用户名和密码是否符合规范
    public boolean isMessageVaild(){
        if(account.getText().toString().trim().equals("")){
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }   else  if(passwd.getText().toString().trim().equals("")){
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getDevice(){

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
