package com.example.gateway2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.gateway2.Device.Device;
import com.example.gateway2.Device.DeviceAdapter;
import com.example.gateway2.HttpUtil.HttpCallbackListener;
import com.example.gateway2.HttpUtil.HttpUtil;
import com.example.gateway2.LogUtil.LogUtil;
import com.example.gateway2.GetBundleUtil.GetBundleData;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowDevice extends AppCompatActivity {
    private RecyclerView deviceList;
    private DeviceAdapter deviceAdapter;
    private Context context;
    private List<Device> list = new ArrayList<>() ;
    private Intent intent;
    private Bundle bundle;
    private GetBundleData getBundleData;
    private String username;
    private String result;
    private String[] deviceStr = this.getResources().getStringArray(R.array.deviceType);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device);
//        获取当前活动的名称
//        Log.i("LYC",getClass().getSimpleName());
        //获取控件
        getWidget();
//        获取数据
//        获取Bundle存储的数据
        bundle = this.getIntent().getExtras();
        username = new GetBundleData().getBundleData(bundle,"username");
//        发送网络请求获取数据
        getData();



    }

    private void getData() {
        String address = "https://lycgg.xyz/gateway/getData.php";
        String postStr = "username=" + username;
        new HttpUtil().sendHttpRequestWithPost(address, postStr, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
//                返回数据处理
                if (DataProcess(response)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShowDevice.this, getString(R.string.getDataSuccess),Toast.LENGTH_SHORT).show();
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowDevice.this);
//                            deviceList.setLayoutManager(linearLayoutManager);
                            // 创建StaggeredGridLayoutManager实例,通过设置其中参数设置显示布局显示方式
//                            交错式布局管理器
//                            此处没有采用回调函数的方式实现多线程之间的矛盾处理,而是在子线程完成之后采取下一步动作
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            deviceList.setLayoutManager(layoutManager);
                            deviceAdapter = new DeviceAdapter(ShowDevice.this,list,bundle);
                            deviceList.setAdapter(deviceAdapter);
                        }
                    });

                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShowDevice.this, getString(R.string.getDataFail),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

/*  数据处理
*  @s 返回的json格式字符串
* 初步将数据根据设备类型分为四组
* */
    @Contract(pure = false)
    private boolean DataProcess(String s) {
        LogUtil.i("获取设备数据:"+ s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = String.valueOf(keys.next());
                String value = jsonObject.getString(key);
                switch (key){
                    case "temp":
                        DataPrase(value,deviceStr[0]);
                        break;
                    case "alarm":
                        DataPrase(value,deviceStr[1]);
                        break;
                    case "light":
                        DataPrase(value,deviceStr[2]);
                        break;
                    case "socket":
                        DataPrase(value,deviceStr[3]);
                        break;
                    default:
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }
// 针对每一个数据进行解析
    private void DataPrase(String s,String deviceType) {
        // 获取的数据已经通过php解决了为空的可能
        try {
            JSONObject jsonObject = new JSONObject(s);
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = (String) keys.next();
                String value = jsonObject.getString(key);
                JSONObject jsonObject1 = new JSONObject(value);
                int deviceNum = jsonObject1.getInt("deviceSNum");
                Device device = new Device(deviceType,deviceNum);
                list.add(device);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getWidget() {
        deviceList = findViewById(R.id.device_list);
    }

}
