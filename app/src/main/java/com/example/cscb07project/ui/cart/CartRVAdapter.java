package com.example.cscb07project.ui.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.google.android.material.elevation.SurfaceColors;

import java.text.NumberFormat;
import java.util.List;

public class CartRVAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private final Context context;
    private final List<CartEntry> cartList;

    public CartRVAdapter(Context context, List<CartEntry> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item_entry, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (cartList.get(position).getStoreName() != null) {
//            TypedValue typedVal =  new TypedValue();
//            context.getTheme().resolveAttribute(com.google.android.material.R.attr.colorSurface, typedVal, true);
//
            holder.getCardView().setCardBackgroundColor(SurfaceColors.SURFACE_1.getColor(context));
            holder.getCardView().setStrokeWidth(0);
            holder.getHeaderLayout().setVisibility(View.VISIBLE);
            holder.getContentLayout().setVisibility(View.GONE);

            holder.getStoreName().setText(cartList.get(position).getStoreName());
        } else {
            holder.getHeaderLayout().setVisibility(View.GONE);
            holder.getContentLayout().setVisibility(View.VISIBLE);

            holder.getItemName().setText(cartList.get(position).getItemName());
            holder.getItemBrand().setText(cartList.get(position).getBrand());
            int quantity_val = cartList.get(position).getQty();

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String priceString = formatter.format(cartList.get(position).getPrice());
            holder.getItemPrice().setText(priceString + (quantity_val > 1 ? " (x" + quantity_val + ")" : ""));

            String img_url_val = cartList.get(position).getImgURL();
            if (img_url_val == null || img_url_val.isEmpty()) {
                holder.getItemImg().setImageResource(R.drawable.ic_launcher_background);
            } else {
                Glide.with(context).load(img_url_val).into(holder.getItemImg());
            }
            holder.getItemModifier().setIcon(context.getDrawable(R.drawable.round_remove_36));
            holder.getItemModifier().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idx = holder.getAdapterPosition();
                    String remItemName = cartList.get(idx).getItemName();
                    while (idx > 0 && cartList.get(idx).getStoreName() == null) {idx--;}
                    String remStoreName = cartList.get(idx).getStoreName();
                    CartPresenter.removeItem(remStoreName, remItemName);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
