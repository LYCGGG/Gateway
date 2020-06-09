package com.example.gateway2.HttpUtil;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
