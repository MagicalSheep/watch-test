package cn.magicalsheep.watchapp.utils;

import java.util.UUID;

public class Util {
    /**
     * 获取设备ID
     *
     * @return ClientID
     */
    public static String getClientID() {
        return UUID.randomUUID().toString();
    }
}
