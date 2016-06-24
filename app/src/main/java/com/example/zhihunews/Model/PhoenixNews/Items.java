package com.example.zhihunews.Model.PhoenixNews;

/**
 * Created by Lxq on 2016/6/21.
 * 用于多Holder实现,维护List<Items>即可
 */

public class Items {

    private SingleItem item;
    private int pos;
    private Object object;

    public Items setItems(SingleItem item, int pos) {
        Items items = new Items();
        items.setItem(item);
        items.setPos(pos);
        return items;
    }

    public SingleItem getItem() {
        return item;
    }

    public void setItem(SingleItem item) {
        this.item = item;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


    /**
     *
     * @param oj
     * @param pos 区分item类型
     * @return
     */
    public Items setIemsWithObject (Object oj,int pos) {
        Items items = new Items();
        items.setObject(oj);
        items.setPos(pos);
        return items;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
