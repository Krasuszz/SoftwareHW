package com.example.softwarehw;

import ohos.aafwk.ability.AbilityPackage;

import ohos.bundle.IBundleManager;
import ohos.data.distributed.common.*;
import ohos.data.distributed.user.SingleKvStore;

import static ohos.security.SystemPermission.DISTRIBUTED_DATASYNC;

public class MyApplication extends AbilityPackage
{
    private static MyApplication instance;
    private SingleKvStore KvStore;
    private KvManager kvManager; //  全局kvmanager对象，通过getinstance被全局调用相应函数接口
    @Override
    public void onInitialize()
    {
        super.onInitialize();
        requestPermission();
        createSingleKvStore();
        instance = this;
    }
    private void requestPermission() //  权限初始化
    {
        if (verifySelfPermission(DISTRIBUTED_DATASYNC) != IBundleManager.PERMISSION_GRANTED)
        {
            if (canRequestPermission(DISTRIBUTED_DATASYNC)) {
                requestPermissionsFromUser(new String[]{DISTRIBUTED_DATASYNC}, 0);
            }
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public SingleKvStore getKvStore() {
        return KvStore;
    }


    public KvManager getKvManager() {
        return kvManager;
    }

    private void createSingleKvStore()
    {
        kvManager = KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(this));
        Options options = new Options();
        options.setCreateIfMissing(true)  // 设置数据库不存在时是否创建
                .setEncrypt(false)  // 设置是否加密
                .setKvStoreType(KvStoreType.SINGLE_VERSION) // 数据库类型
                .setAutoSync(true);
        KvStore = kvManager.getKvStore(options, "KvStore");
    }
}
