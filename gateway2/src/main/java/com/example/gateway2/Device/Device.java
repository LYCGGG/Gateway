package com.example.gateway2.Device;

public class Device {
//    两个参数分别代表设备的类型和设备的编号
    private String deviceType;
    //    private int deviceNum;
    private int deviceNum;

    public Device(String deviceType, int deviceNum) {
        this.deviceType = deviceType;
        this.deviceNum = deviceNum;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public int getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDeviceNum(int deviceNum) {
        this.deviceNum = deviceNum;
    }
}
