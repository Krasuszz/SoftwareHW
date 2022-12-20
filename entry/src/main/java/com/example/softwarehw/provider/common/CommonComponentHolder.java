package com.example.softwarehw.provider.common;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共类-组件持有者
 */
public class CommonComponentHolder {

    private final Component convertComponent;
    private final Map<Integer, Component> mapComponent = new HashMap<>();

    public CommonComponentHolder(Context mContext, int xmlId, ComponentContainer parent) {
        convertComponent = LayoutScatter.getInstance(mContext).parse(xmlId, parent, false);
        convertComponent.setTag(this);
    }

    public static CommonComponentHolder get(Context mContext, int xmlId, Component convertComponent, ComponentContainer parent) {
        if (convertComponent == null) {
            return new CommonComponentHolder(mContext, xmlId, parent);
        } else {
            return (CommonComponentHolder) convertComponent.getTag();
        }
    }

    /**
     * 返回根布局
     */
    public Component getConvertComponent() {
        return convertComponent;
    }

    /**
     * 从map缓存中获取 组件
     */
    public <T extends Component> T getComponent(int componentId) {
        Component component = mapComponent.get(componentId);
        if (component == null) {
            component = convertComponent.findComponentById(componentId);
            mapComponent.put(componentId, component);
        }
        return (T) component;
    }


}
