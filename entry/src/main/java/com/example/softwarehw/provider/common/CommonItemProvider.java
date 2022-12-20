package com.example.softwarehw.provider.common;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.app.Context;

import java.util.List;

/**
 * 公共列表
 */
public abstract class CommonItemProvider<T> extends BaseItemProvider {
    /**上下文对象**/
    protected Context mContext;
    /**列表数据**/
    protected List<T> listData;

    public CommonItemProvider(Context mContext, List<T> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public T getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer parent) {
        CommonComponentHolder holder=CommonComponentHolder.get(mContext,getXmlId(),convertComponent,parent);
        convert(holder,position,getItem(position));
        return holder.getConvertComponent();
    }

    //====================================子类实现的方法=============================================
    public abstract int getXmlId();
    public abstract void convert(CommonComponentHolder holder,int position,T data);

}
