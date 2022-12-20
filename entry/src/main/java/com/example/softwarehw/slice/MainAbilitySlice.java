package com.example.softwarehw.slice;

import com.example.softwarehw.ResourceTable;
import com.example.softwarehw.bean.ChatDataBean;
import com.example.softwarehw.provider.ChatProvider;
import com.example.softwarehw.util.Tools;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TextField;
import ohos.app.Context;
import ohos.bundle.IBundleManager;
import ohos.data.distributed.common.*;
import ohos.data.distributed.user.SingleKvStore;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.util.ArrayList;
import java.util.List;

import static ohos.security.SystemPermission.DISTRIBUTED_DATASYNC;

public class MainAbilitySlice extends AbilitySlice {
    private Context mContext;
    // 聊天列表
    private ListContainer lcList;
    // 聊天数据
    private final List<ChatDataBean> listData = new ArrayList<>();
    // 聊天数据适配器
    private ChatProvider chatProvider;
    // 输入框
    private TextField tfContent;
    // 发送按钮
    private Button btnSend;

    // 分布式数据库管理器
    private KvManager kvManager;
    // 分布式数据库
    private SingleKvStore singleKvStore;
    // 数据库名称
    private static final String STORE_NAME = "ChatStore";
    // 存入的列表数据key
    private static final String KEY_DATA = "key_data";
    // 存入的头像索引
    private static final String KEY_PIC_INDEX = "key_pic_index";
    private int picIndex = 0;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        mContext = this;
        requestPermission();
        initComponent();
        initDatabase();
    }


    /**
     * 请求分布式权限
     */
    private void requestPermission() {
        if (verifySelfPermission(DISTRIBUTED_DATASYNC) != IBundleManager.PERMISSION_GRANTED) {
            if (canRequestPermission(DISTRIBUTED_DATASYNC)) {
                requestPermissionsFromUser(new String[]{DISTRIBUTED_DATASYNC}, 0);
            }
        }
    }

    /**
     * 初始化组建
     */
    private void initComponent() {
        lcList = (ListContainer) findComponentById(ResourceTable.Id_lc_list);
        tfContent = (TextField) findComponentById(ResourceTable.Id_tf_content);
        tfContent.setAdjustInputPanel(true);
        btnSend = (Button) findComponentById(ResourceTable.Id_btn_send);
        btnSend.setEnabled(false);

        // 初始化适配器
        chatProvider = new ChatProvider(mContext, listData);
        lcList.setItemProvider(chatProvider);

        // 输入框内容变化监听
        tfContent.addTextObserver((text, start, before, count) -> {
            btnSend.setEnabled(text.length() != 0);
        });

        // 点击发送按钮
        btnSend.setClickedListener(component -> {
            String content = tfContent.getText().trim();
            listData.add(new ChatDataBean(Tools.getDeviceId(mContext), picIndex, content));
            // 存入数据库中
            singleKvStore.putString(KEY_DATA, ZSONObject.toZSONString(listData));
            // 清空输入框
            tfContent.setText("");
        });
    }

    /**
     * 初始化分布式数据库
     */
    private void initDatabase() {
        kvManager = KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(this));

        // 数据库配置

        Options options = new Options();
        options.setCreateIfMissing(true)  // 设置数据库不存在时是否创建
                .setEncrypt(false)  // 设置是否加密
                .setKvStoreType(KvStoreType.SINGLE_VERSION); // 数据库类型

        // 创建分布式数据库
        singleKvStore = kvManager.getKvStore(options, STORE_NAME);

        // 监听数据库数据改变
        singleKvStore.subscribe(SubscribeType.SUBSCRIBE_TYPE_ALL, new KvStoreObserver() {
            @Override
            public void onChange(ChangeNotification changeNotification) {
                KvStoreObserver.super.onChange(changeNotification);
                List<Entry> insertEntries = changeNotification.getInsertEntries();
                List<Entry> updateEntries = changeNotification.getUpdateEntries();

                // 第一次存入数据， 获取insertEntries
                if (insertEntries.size() > 0) {
                    for (Entry entry : insertEntries) {  // 遍历insertEntries
                        if (KEY_DATA.equals(entry.getKey())) {  // 找到entry中key相同的
                            // 回调为非UI线程， 需要在UI更新线程
                            getUITaskDispatcher().syncDispatch(() -> {
                                listData.clear();
                                listData.addAll(ZSONArray.stringToClassList(entry.getValue().getString(), ChatDataBean.class));
                                chatProvider.notifyDataChanged();
                                lcList.scrollTo(listData.size() -1 );
                            });
                        }
                    }
                }
                else if (updateEntries.size() > 0){
                    for (Entry entry : updateEntries) {
                        if (KEY_DATA.equals(entry.getKey())) {
                            getUITaskDispatcher().syncDispatch(() -> {
                                listData.clear();
                                listData.addAll(ZSONArray.stringToClassList(entry.getValue().getString(), ChatDataBean.class));
                                chatProvider.notifyDataChanged();
                                lcList.scrollTo(listData.size()-1);
                            });
                        }
                    }
                }
            }
        });

        try {
            picIndex = singleKvStore.getInt(KEY_PIC_INDEX);  // 找到数据库中“key_pic_index”的queries的int值
            singleKvStore.putInt(KEY_PIC_INDEX, picIndex + 1);
        }
        catch (KvStoreException e) {
            e.printStackTrace();

            // 没有找到，首次进入
            if (e.getKvStoreErrorCode() == KvStoreErrorCode.KEY_NOT_FOUND) {
                picIndex = 0;
                singleKvStore.putInt(KEY_PIC_INDEX, picIndex + 1);
            }
        }

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
