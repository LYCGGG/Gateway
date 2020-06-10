package com.example.gateway2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gateway2.R;
import com.example.gateway2.ShowDevice;

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



    //    获取数据
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }
}
