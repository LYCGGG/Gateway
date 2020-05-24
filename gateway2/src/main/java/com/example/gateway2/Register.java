package com.example.gateway2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Register extends Activity {
    protected EditText account;
    protected EditText passwd;
    protected EditText passwdCheck;
    int returnResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        account = findViewById(R.id.account);
        passwd = findViewById(R.id.passwd);
        passwdCheck = findViewById(R.id.passwdCheck);
        Button confirm = findViewById(R.id.changepwd_btn);
        Button cancel = findViewById(R.id.cancel);

        confirm.setOnClickListener(reg_Listener);
        cancel.setOnClickListener(reg_Listener);

    }
    View.OnClickListener reg_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.changepwd_btn:
                    register_confirm();
                    break;
                case R.id.cancel:
                    finish();
            }
        }
    };
    private void register_confirm(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//              子线程如何联通this
//               三种方法：Looper.prepare();.  在Activity 可用runUiThread,  Handler
//                Looper.prepare();
//                Toast.makeText(Register.this,getString(R.string.test),Toast.LENGTH_SHORT).show();
                if(isMessageVaild()){
                    String UserAccount = account.getText().toString().trim();
                    String UserPwd = passwd.getText().toString().trim();
                    String UserPwdCheck = passwdCheck.getText().toString().trim();
                    try {
                        URL obj = new URL("https://lycgg.xyz/gateway/register.php");
                        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                        if (UserPwd.equals(UserPwdCheck) == false){
                            Toast.makeText(Register.this, getString(R.string.pwd_no_equal), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            String registerStr = "username=" + UserAccount + "&passwd=" + UserPwd;
                            //添加请求头
                            con.setRequestMethod("POST");
                            con.setDoOutput(true);
                            con.setDoInput(true);
                            con.connect();

                            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                            wr.writeBytes(registerStr);
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

                                Log.i("LYC",String.valueOf(returnResult));
                                //子线程不能刷新父线程UI
                            } catch (JSONException e) {
                                Log.e("log_tag", "the Error parsing data "+e.toString());
                            }
                            //刷新父线程UI方法：
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (returnResult == 1) {
                                        Toast.makeText(Register.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(Register.this, getString(R.string.register_fail), Toast.LENGTH_SHORT).show();
                                        //return;
                                    }
                                }
                            });
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                Looper.loop();
            }
        }).start();
    }

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
}
