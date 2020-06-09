package com.example.gateway2.HttpUtil;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static void sendHttpRequestWithPost(final String address, final String PostStr, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection conn = null;
                try {
                    conn = (HttpsURLConnection) new URL(address).openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setReadTimeout(4000);
                    conn.setConnectTimeout(4000);
                    conn.connect();
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(PostStr);
                    if (wr != null){
                        wr.close();
                    }
//                    读取返回数据
                    BufferedReader out = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer response=new StringBuffer();
                    String line;
                    while((line=out.readLine())!=null){
                        response.append(line);
                    }
                    out.close();
                    if(listener!=null){
                        listener.onFinish(response.toString());
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null){
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }




//    public static void sentHttpRequest(final String address, final okhttp3.Callback callback){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url(address).build();
//                client.newCall(request).enqueue(callback);
//            }
//        }).start();
//
//    }

}
