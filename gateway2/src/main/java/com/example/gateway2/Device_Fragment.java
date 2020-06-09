package com.example.gateway2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Device_Fragment extends Fragment {
    private Button getDeviceData_btn;
    private String username;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_fragment,null);
//        Log.i("LYC","device");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDeviceData_btn = getActivity().findViewById(R.id.getDeviceData);
        getDeviceData_btn.setOnClickListener(l);
        bundle = getActivity().getIntent().getExtras();
        username = getData(bundle,"username");
    }
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.getDeviceData){
                getDeviceData();
            }
        }
    };

    private void getDeviceData() {
        Intent intent = new Intent(getActivity(), ShowDevice.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    //    private void getDeviceData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL obj = new URL("https://lycgg.xyz/gateway/getUserDeviceData.php");
//                    HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
//                    String getDeviceDataStr = "username=" + username;
//                    Log.i("LYC",getDeviceDataStr);
//                    con.setRequestMethod("POST");
//                    con.setDoOutput(true);
//                    con.setDoInput(true);
//                    con.connect();
//                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//                    wr.writeBytes(getDeviceDataStr);
//                    wr.close();
////                    获取返回数据
//                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String inputLine;
//                    final StringBuffer respnse = new StringBuffer();
//                    while ((inputLine = in.readLine()) != null){
//                        respnse.append(inputLine);
//                    }
//                    String result = respnse.toString();
//                    Log.i("LYC",result);
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        Iterator<String> iterator = jsonObject.keys();
//                        while (iterator.hasNext()){
//                            String testkey = iterator.next();
//                            String testvalue = jsonObject.getString(testkey);
//                            Log.i("LYC",testkey + ":" + testvalue);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
    //    获取数据
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }
}
