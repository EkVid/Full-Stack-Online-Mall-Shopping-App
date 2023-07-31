package com.example.cscb07project.ui.cart;

import com.example.cscb07project.ui.itemlist.ItemListEntry;

import java.util.List;

public class CartPresenter {

    private final CartFragment fragment;
    private final CartModel model;
    private CartRVAdapter adapter;

    public CartPresenter(CartFragment fragment) {
        this.fragment = fragment;
        this.model = new CartModel(this, "https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/");
        model.createEventListener();
    }

    public void setAdapter(List<CartEntry> items) {
        adapter = new CartRVAdapter(fragment.getContext(), items);
        fragment.setAdapter(adapter);
    }
}
