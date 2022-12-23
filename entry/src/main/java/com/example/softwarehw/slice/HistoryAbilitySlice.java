package com.example.softwarehw.slice;

import com.example.softwarehw.bean.ChatDataBean;
import com.example.softwarehw.bean.UserDataBean;
import com.example.softwarehw.util.HistoryItem;
import com.example.softwarehw.ResourceTable;
import com.example.softwarehw.provider.HistoryProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HistoryAbilitySlice extends AbilitySlice {
    private final List<ChatDataBean> listData = new ArrayList<>();
    // 输入框
    private TextField tfContent;
    // 搜索按钮
    private Button btnSearch;
    private String contain_input;
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x12345, "HistoryAbilitySlice");
    @Override
    public void onStart(Intent intent)
    {
        super.onStart(intent);
        get_info(intent);
        // 加载XML布局
        super.setUIContent(ResourceTable.Layout_ability_history);

        tfContent = (TextField) findComponentById(ResourceTable.Id_tf_content_history);
        tfContent.setAdjustInputPanel(true);
        btnSearch= (Button) findComponentById(ResourceTable.Id_btn_search_history);
        btnSearch.setEnabled(true);
        btnSearch.setClickedListener(this::onClick);
        initListContainer();
    }
    void get_info(Intent intent) //  从login页面获取传参：用户id
    {
        String listData_str="{}";
        Operation operation = intent.getOperation();
        IntentParams intentParams = intent.getParams();
        listData_str = (String)intentParams.getParam("listData");
        listData.clear();
        listData.addAll(ZSONArray.stringToClassList(listData_str, ChatDataBean.class));
        HiLog.info(label, "listData_get:"+ listData_str);
        HiLog.info(label, "listData_convert:"+ ZSONObject.toZSONString(ZSONArray.stringToClassList(listData_str, ChatDataBean.class)));
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }


    private final List<HistoryItem> list_history = new ArrayList<>();
    private HistoryProvider sampleItemProvider;
    public void onClick(Component component)
    {
        if(component == btnSearch)
        {
            contain_input = tfContent.getText();
            if(contain_input==null) contain_input = "";
            getData(contain_input);
            sampleItemProvider.notifyDataChanged();
        }
    }
    private void initListContainer()
    {
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_lc_list_history);
        getData("");
        sampleItemProvider = new HistoryProvider(list_history, this);
        listContainer.setItemProvider(sampleItemProvider);
        /*
        listContainer.setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                // ListContainer初始化时数据统一在provider中创建，不直接调用这个接口；
                // 建议在onComponentBoundToWindow监听或者其他事件监听中调用。
                sampleItemProvider.notifyDataChanged();
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {}
        });
        */
    }
    private void getData(String contain)
    {
        String sender = "", send_account = "", receive_account = "", content = "",date = "", time = "";
        Iterator<ChatDataBean> it = listData.iterator();
        ArrayList<HistoryItem> list = new ArrayList<>();
        for (int i=0;it.hasNext();++i)
        {
            ChatDataBean s=it.next();
            sender = s.getSender();
            send_account = s.getSend_account();
            receive_account = s.getReceive_account();
            content = s.getContent();
            date = s.getDate();
            time = s.getTime();
            if(content.contains(contain))
            {
                list.add(new HistoryItem("Item"+ i,send_account + ": " + date +" " + time, content));
            }
            else if(s.getTime().contains(contain))
            {
                list.add(new HistoryItem("Item"+ i,send_account + ": " + date +" " + time, content));
            }
        }
        list_history.clear();
        list_history.addAll(list);
    }
}

