package com.example.softwarehw.provider;
import com.example.softwarehw.util.HistoryItem;
import com.example.softwarehw.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.List;
public class HistoryProvider extends BaseItemProvider {
    private List<HistoryItem> list;
    private AbilitySlice slice;
    public HistoryProvider(List<HistoryItem> list, AbilitySlice slice) {
        this.list = list;
        this.slice = slice;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public Object getItem(int position) {
        if (list != null && position >= 0 && position < list.size()){
            return list.get(position);
        }
        return null;
    }
    @Override
    public long getItemId(int position) {
        //可添加具体处理逻辑

        return position;
    }
    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_history, null, false);
        } else {
            cpt = convertComponent;
        }
        HistoryItem sampleItem = list.get(position);
        Text info = (Text) cpt.findComponentById(ResourceTable.Id_text_item_hostory_info);
        Text message = (Text) cpt.findComponentById(ResourceTable.Id_text_item_hostory_message);
        info.setText(sampleItem.getInfo());
        message.setText(sampleItem.getMessage());
        return cpt;
    }
}