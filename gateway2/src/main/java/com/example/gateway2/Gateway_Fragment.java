package com.example.gateway2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Gateway_Fragment extends Fragment {
    private Button addDevice_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gateway_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addDevice_btn = getActivity().findViewById(R.id.add_device);
        addDevice_btn.setOnClickListener(l);
    }
    private void jumpToAddDevice() {
        Bundle bundle = getActivity().getIntent().getExtras();
        String username = getData(bundle,"username");
        Bundle bundle1 = new Bundle();
        bundle1.putString("username",username);
        Intent intent = new Intent(getActivity(),AddDevice.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.add_device:
                    jumpToAddDevice();

            }

        }


    };
    //    获取数据
    private String getData(Bundle bundle, String key){
        String string = bundle.getString(key);
        return string;
    }
}
