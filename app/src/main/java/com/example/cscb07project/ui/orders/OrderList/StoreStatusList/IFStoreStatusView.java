package com.example.cscb07project.ui.orders.OrderList.StoreStatusList;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

public interface IFStoreStatusView {
    Context getViewContext();
    BaseExpandableListAdapter getAdapter();
    void setAdapter(BaseExpandableListAdapter adapter);
}
