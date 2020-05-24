package com.example.gateway2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ChangePwd extends AppCompatActivity {
//    private TextView username;
    protected EditText passwd;
    protected EditText passwdChanged;
    protected EditText passwdChangedCheck;
    private Button changedPwd;
    private Button cancel;
    private String username;
    private int returnResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i("LYC","asdfa");
        setContentView(R.layout.changepwd);
//
//        username = findViewById(R.id.username);
        Bundle bundle = this.getIntent().getExtras();
        username = getData(bundle,"username");
        passwd = findViewById(R.id.passwd);
        passwdChanged = findViewById(R.id.passwd_changed);
        passwdChangedCheck = findViewById(R.id.passwd_changedcheck);
        changedPwd = findViewById(R.id.changepwd_btn);
        cancel = findViewById(R.id.cancel);

        changedPwd.setOnClickListener(l);
        cancel.setOnClickListener(l);
        Log.i("LYC","ChangedTest");
    }

    //    获取数据
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }
    private void changePwd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String username_text = username.getText().toString().trim();
                String passwd_text = passwd.getText().toString().trim();
                String passwdChanged_text = passwdChanged.getText().toString().trim();
                String passwdChangedCheck_text = passwdChangedCheck.getText().toString().trim();
                try {
                    URL obj = new URL("https://lycgg.xyz/gateway/changePwd.php");
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                    if(passwdChanged_text.equals(passwdChangedCheck_text) == false) {
                        Toast.makeText(ChangePwd.this,getString(R.string.changedPwd_not_equal),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (passwd_text.equals("")){
                        Toast.makeText(ChangePwd.this,getString(R.string.pwd_empty),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String changePwdStr = "username="+username + "&passwd="+passwd_text+"&passwdChanged=" + passwdChanged_text;
                        //添加请求头
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.connect();
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.writeBytes(changePwdStr);
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

//                            Log.i("LYC",String.valueOf(returnResult));
                            //子线程不能刷新父线程UI
                        } catch (JSONException e) {
                            switch (Log.e("log_tag", "the Error parsing data " + e.toString())) {
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(returnResult == 1){
                                    Toast.makeText(ChangePwd.this, getString(R.string.changedPwdSuccess), Toast.LENGTH_SHORT).show();
                                    finish();
                                }else if (returnResult == 0){
                                    Toast.makeText(ChangePwd.this, getString(R.string.changedPwdFail), Toast.LENGTH_SHORT).show();
                                    //return;
                                } else if (returnResult == -1){
                                    Toast.makeText(ChangePwd.this,getString(R.string.pwd_error),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.changepwd_btn:
//                    确认修改密码
                    changePwd();
                    break;
                case R.id.cancel:
//                    返回
                    finish();
                    break;
            }
        }


    };
}
