package com.example.gateway2.DeviceControl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gateway2.HttpUtil.HttpCallbackListener;
import com.example.gateway2.HttpUtil.HttpUtil;
import com.example.gateway2.LogUtil.LogUtil;
import com.example.gateway2.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceTemp extends AppCompatActivity {
    TextView tempNum;
    TextView tempTemp;
    TextView tempHumi;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_temp);
        bundle = getIntent().getExtras();
//        String username = bundle.getString("username");
//        控件绑定
        FindWidget();
//        控件初始化
        WidgetInit();
//        网络请求
        getTempData();
    }

    private void WidgetInit() {
        tempNum.setText(bundle.getString("deviceNum"));
    }

    private void getTempData() {
        String address = "https://lycgg.xyz/gateway/getUserTempData.php";
        String postStr = "username=" + bundle.getString("username") + "&deviceNum=" + bundle.getString("deviceNum");
        LogUtil.i(postStr);
        new HttpUtil().sendHttpRequestWithPost(address, postStr, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                DataProcess(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DeviceTemp.this,"获取数据成功",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void DataProcess(String s) {
        LogUtil.i(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            tempTemp.setText(jsonObject.getString("temperature"));
            tempHumi.setText(jsonObject.getString("humidity"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void FindWidget() {
        tempNum = findViewById(R.id.deviceSNumText);
        tempTemp = findViewById(R.id.tempText);
        tempHumi = findViewById(R.id.humiText);
    }
}
