package com.example.softwarehw.util;

import ohos.app.Context;
import ohos.data.distributed.common.KvManagerConfig;
import ohos.data.distributed.common.KvManagerFactory;

public class Tools {
    /**
     * 获取设备Id
     */
    public static String getDeviceId(Context mContext){
        return KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(mContext)).getLocalDeviceInfo().getId();
    }
}
