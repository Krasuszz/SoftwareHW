package com.example.softwarehw.provider;


import com.example.softwarehw.ResourceTable;
import com.example.softwarehw.provider.common.CommonComponentHolder;
import com.example.softwarehw.provider.common.CommonItemProvider;
import com.example.softwarehw.util.Tools;
import ohos.agp.components.*;
import ohos.app.Context;
import com.example.softwarehw.bean.ChatDataBean;

import java.util.List;

public class ChatProvider extends CommonItemProvider<ChatDataBean> {

    public ChatProvider(Context mContext, List<ChatDataBean> listData) {
        super(mContext, listData);
    }

    @Override
    public int getXmlId() {
        return ResourceTable.Layout_item_chat;
    }

    private int pics[] = new int[]{
            ResourceTable.Media_icon_pic0, ResourceTable.Media_icon_pic1, ResourceTable.Media_icon_pic2,
            ResourceTable.Media_icon_pic3, ResourceTable.Media_icon_pic4, ResourceTable.Media_icon_pic5,
            ResourceTable.Media_icon_pic6, ResourceTable.Media_icon_pic7,
    };

    @Override
    public void convert(CommonComponentHolder holder, int position, ChatDataBean data) {
        Component divider = holder.getComponent(ResourceTable.Id_component_item_chat_divider);
        DirectionalLayout dlLeft = holder.getComponent(ResourceTable.Id_dl_item_chat_left);
        Image imageLeftPic = holder.getComponent(ResourceTable.Id_image_item_chat_left_pic);
        Text textLeftContent = holder.getComponent(ResourceTable.Id_text_item_chat_left_content);
        DirectionalLayout dlRight = holder.getComponent(ResourceTable.Id_dl_item_chat_right);
        Image imageRightPic = holder.getComponent(ResourceTable.Id_image_item_chat_right_pic);
        Text textRightContent = holder.getComponent(ResourceTable.Id_text_item_chat_right_content);

        divider.setVisibility(position == 0 ? Component.VISIBLE : Component.HIDE);
        imageLeftPic.setCornerRadius(AttrHelper.vp2px(5, mContext));
        imageRightPic.setCornerRadius(AttrHelper.vp2px(5, mContext));

        // 发送者是自己
        if(data.getSender().equals(Tools.getDeviceId(mContext))){
            dlRight.setVisibility(Component.VISIBLE);
            dlLeft.setVisibility(Component.HIDE);
            textRightContent.setText(data.getContent());
            imageRightPic.setPixelMap(pics[data.getPicIndex()]);
        }else{
            dlRight.setVisibility(Component.HIDE);
            dlLeft.setVisibility(Component.VISIBLE);
            textLeftContent.setText(data.getContent());
            imageLeftPic.setPixelMap(pics[data.getPicIndex()]);
        }
    }
}
