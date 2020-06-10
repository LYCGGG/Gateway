package com.example.gateway2.Device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gateway2.DeviceControl.DeviceAlarm;
import com.example.gateway2.DeviceControl.DeviceLight;
import com.example.gateway2.DeviceControl.DeviceSocket;
import com.example.gateway2.DeviceControl.DeviceTemp;
import com.example.gateway2.LogUtil.LogUtil;
import com.example.gateway2.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private View inflater;
    private Context context;
    private List<Device> list;
    private Bundle bundle;
//    private String[] deviceStr = context.getResources().getStringArray(R.array.deviceType);

    public DeviceAdapter(Context context, List<Device> list, Bundle bundle1) {
        this.context = context;
        this.list = list;
        bundle = bundle1;
    }
//创建每个子项的布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(inflater);
//        viewHolder.itemView.setOnClickListener(l);
//        每个item点击事件响应
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                String deviceType = (String) viewHolder.deviceType.getText();
//                String deviceNum = (String) viewHolder.deviceNum.getText();
                String deviceNum = String.valueOf(list.get(position).getDeviceNum());
//                Toast.makeText(parent.getContext(),"you clicked view "+ deviceType,Toast.LENGTH_SHORT).show();
                Intent intent;
                bundle.getString("username");
                bundle.putString("deviceNum", deviceNum);
                LogUtil.i(bundle.getString("username"));
                LogUtil.i(bundle.getString("deviceNum"));
                if (deviceType.equals("温湿度传感器")){
                    Toast.makeText(context,"跳转到传感器界面",Toast.LENGTH_LONG).show();
                    intent = new Intent(context,DeviceTemp.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
                else if (deviceType.equals("灯光")){
                    Toast.makeText(context,"跳转到灯光界面",Toast.LENGTH_LONG).show();
                    intent = new Intent(context, DeviceLight.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else if (deviceType.equals("报警器")){
                    Toast.makeText(context,"跳转到报警器界面",Toast.LENGTH_LONG).show();
                    intent = new Intent(context, DeviceAlarm.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }else if (deviceType.equals("电量监控")){
                    Toast.makeText(context,"跳转到电量监控界面",Toast.LENGTH_LONG).show();
                    intent = new Intent(context, DeviceSocket.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }

//                switch不能匹配字符串
//                switch (deviceType){
//                    case deviceStr[0]:
//                        intent = new Intent(context, DeviceTemp.class);
//                        break;
//                    case deviceStr[1]:
//                        break;
//                    case deviceStr[2]:
//                        break;
//                    case deviceStr[3]:
//                        break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + deviceType);
//                }
            }
        });
        return viewHolder;
    }
//负责给每个子项绑定数据
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Device device = list.get(position);
        holder.deviceType.setText(device.getDeviceType());
        holder.deviceNum.setText("Num："+device.getDeviceNum());
//        holder.deviceIcon.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView deviceType;
        private TextView deviceNum;
        private ImageView deviceIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceType = itemView.findViewById(R.id.deviceType);
            deviceNum = itemView.findViewById(R.id.deviceNum);

        }
    }

//    View.OnClickListener l = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        }
//    };
}
