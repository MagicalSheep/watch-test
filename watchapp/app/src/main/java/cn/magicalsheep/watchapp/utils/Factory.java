package cn.magicalsheep.watchapp.utils;

import cn.magicalsheep.watchapp.mqtt.DeviceClient;

public class Factory {
    private static String host = "tcp://platform.magicalsheep.cn";
    private static String port = "1883";
    private static String uuid = Util.getClientID();
    private static DeviceClient deviceClient;

    public static String getUuid() {
        return uuid;
    }

    public static void initDeviceClient(){
        deviceClient = new DeviceClient(host, port, uuid);
    }

    public static DeviceClient getDeviceClient(){
        return deviceClient;
    }
}
