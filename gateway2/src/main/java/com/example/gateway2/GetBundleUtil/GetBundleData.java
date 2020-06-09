package com.example.gateway2.GetBundleUtil;

import android.os.Bundle;

public class GetBundleData {
    Bundle bundle;
    String key;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getBundleData(Bundle bundle,String key){
        return bundle.getString(key);
    }
}
