package com.example.cscb07project.ui.orders.OrderList.StoreStatusList;

public interface IFStoreStatusPresenter {
    void onStart();
    void onDestroyView();
    void notifyAdapterDataChanged();
    int getGroupCount();
    int getChildrenCount(int i);
    Object getGroup(int i);
    Object getChild(int i, int i1);
    long getGroupId(int i);
    long getChildId(int i, int i1);
    String getStoreName(int i);
    boolean getStoreComplete(int i);
    String getItemName(int i, int i1);
    int getItemQty(int i, int i1);
    String getStoreImageURL(int i);
    String getItemImageURL(int i, int i1);
}
