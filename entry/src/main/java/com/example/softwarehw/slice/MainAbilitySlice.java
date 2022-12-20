package com.example.softwarehw.slice;

import com.example.softwarehw.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TextField;
import ohos.app.Context;
import ohos.data.distributed.common.KvManager;
import ohos.data.distributed.user.SingleKvStore;
import bean.ChatDataBean;


import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
