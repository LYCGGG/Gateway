package com.example.gateway2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddDevice extends AppCompatActivity {
    private String deviceText;
    private String username;
    private EditText deviceSNum;
    private int returnResult;
    private int initResult;
    int deviceNum = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);
        Spinner spinner = findViewById(R.id.add_device_spinner);
        Button confirm = findViewById(R.id.confirm);
        deviceSNum = findViewById(R.id.deviceType);
        confirm.setOnClickListener(AddDevice_Linstener);
        spinner.setOnItemSelectedListener(AddDevice_Spinner_Linstener);

        Bundle bundle = this.getIntent().getExtras();
        username = getData(bundle,"username");



    }

    //    获取数据
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }

    AdapterView.OnItemSelectedListener AddDevice_Spinner_Linstener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
            String[] deviceTextArray = getResources().getStringArray(R.array.Devices);
            deviceText = deviceTextArray[pos];
            deviceNum = pos;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    View.OnClickListener AddDevice_Linstener = new View.OnClickListener() {
//        String deviceSNum_Text = deviceSNum.getText().toString().trim();
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.confirm:
                    if (deviceNum == 0) {
                        Toast.makeText(AddDevice.this,getString(R.string.chooseAddDevice),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (isEmpty()){
                            AddDevice_Confirm(deviceNum);
                        }
                    }
                    break;
//                    AddDevice_Confirm();
                case R.id.cancel:
//                    取消添加设备
                    finish();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    };
    private void AddDevice_Confirm(final int deviceNum) {
            new Thread(new Runnable() {
            String deviceSNum_Text = deviceSNum.getText().toString().trim();

            @Override
            public void run() {
//                if (deviceSNum_Text.equals("")) {
                Log.i("LYC", deviceSNum_Text);
                try {
                        URL obj = new URL("https://lycgg.xyz/gateway/addDevice.php");
                        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                        String addDeviceStr = "username=" + username + "&deviceNum=" + deviceNum + "&deviceSNum=" + deviceSNum_Text;
                        Log.i("LYC", addDeviceStr);
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.connect();
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.writeBytes(addDeviceStr);
                        wr.close();
                        //                    读取返回数据
                        //                    建立缓冲区
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        final StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);//数据写入
                        }
                        in.close();
                        String result = response.toString();

                        Log.i("LYC",result);
                        try {
                            //                        获取服务器返回的数据,json格式
                            JSONObject jsonObject = new JSONObject(result);
                            returnResult = jsonObject.getInt("status");
                            initResult = jsonObject.getInt("init");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //                    显示
                        showResult();
                    } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showResult(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(returnResult == 1 && initResult == 1){
                    Toast.makeText(AddDevice.this,getString(R.string.addDeviceSuccess),Toast.LENGTH_SHORT).show();
                }else if (returnResult == 0){
                    Toast.makeText(AddDevice.this,getString(R.string.addDeviceFail),Toast.LENGTH_SHORT).show();
                } else if (returnResult == -1){
                    Toast.makeText(AddDevice.this,getString(R.string.getFileFail),Toast.LENGTH_SHORT).show();
                } else if (returnResult == -2){
                    Toast.makeText(AddDevice.this,getString(R.string.initDeviceFail),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddDevice.this,getString(R.string.insertDeviceFail),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean isEmpty(){
        if (deviceSNum.getText().toString().trim().equals("")){
            Toast.makeText(AddDevice.this, getString(R.string.getSNumFail), Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}
