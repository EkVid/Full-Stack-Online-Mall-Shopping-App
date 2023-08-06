package com.example.mallapp.ui.ItemList;

import static com.example.mallapp.MainActivity.currentUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cscb07project.R;
import com.example.mallapp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.util.List;



public class ItemListRVAdapter extends RecyclerView.Adapter<ItemListRVAdapter.ItemListViewHolder> {

    private final Context context;

    private final ItemListPresenter presenter;
    private final List<ItemListEntry> itemsList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public ItemListRVAdapter(Context context, List<ItemListEntry> itemsList, ItemListPresenter presenter) {
        this.context = context;
        this.itemsList = itemsList;
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.getItemName().setText(itemsList.get(position).getItemName());
        holder.getItemPrice().setText(String.valueOf(itemsList.get(position).getPrice()));
        holder.getItemBrand().setText(itemsList.get(position).getBrand());

//        String username = "user2";
//        String store = MainActivity.getStoreBundle().getString(MainActivity.getStoreBundleKey());
//        Getqty getqty = new Getqty(username, itemsList.get(position).getItemName(), store);
//        getqty.getQuantityFromFirebase(new Getqty.QuantityCallback() {
//            @Override
//            public void onQuantityReceived(int quantity) {
//                holder.getAmount().setText(quantity + " in cart");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                return;
//            }
//        });

        if (itemsList.get(position).getImgURL() != null && !itemsList.get(position).getImgURL().isEmpty()) {
            Glide.with(context).load(itemsList.get(position).getImgURL()).into(holder.getItemLogo());
        } else {
            holder.getItemLogo().setImageResource(R.drawable.placeholder_item_icon);
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
    public class ItemListViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemName;
        private final TextView itemPrice;
        private final ImageView itemLogo;

        private final TextView itemBrand;

        private final ImageButton button_add;

        private final TextView detailed_itemDescription;


        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_entry_name);
            itemPrice = itemView.findViewById(R.id.item_entry_price);
            itemLogo = itemView.findViewById(R.id.item_entry_img);
            itemBrand = itemView.findViewById(R.id.item_entry_brand);
            button_add = itemView.findViewById(R.id.item_entry_modifier);

            button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    String itemname = presenter.getItem(position);
                    String storename = MainActivity.getStoreBundle().getString(MainActivity.getStoreBundleKey());
                    Add add = new Add(currentUser, storename, itemname,1);
                    add.addToFirebase();
                    Toast.makeText(context, "item added", Toast.LENGTH_SHORT).show();
                }
            });

            detailed_itemDescription = itemView.findViewById(R.id.item_entry_description);

            itemLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    ItemListPresenter.item_name = presenter.getItem(position);
                    ItemListPresenter.item_description= presenter.getDescription(position);
                    ItemListPresenter.item_brand = presenter.getitemBrand(position);
                    ItemListPresenter.item_image = presenter.getLogo(position);
                    Navigation.findNavController(view).navigate(R.id.action_item_list_to_detailed_list);
                }
            });

            itemName.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    ItemListPresenter.item_name = presenter.getItem(position);
                    ItemListPresenter.item_description= presenter.getDescription(position);
                    ItemListPresenter.item_brand = presenter.getitemBrand(position);
                    ItemListPresenter.item_image = presenter.getLogo(position);
                    Navigation.findNavController(view).navigate(R.id.action_item_list_to_detailed_list);
                }
            });
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemPrice() {
            return itemPrice;
        }

        public ImageView getItemLogo() {
            return itemLogo;
        }

        public TextView getItemBrand(){return itemBrand;}

        public TextView getDetailed_itemDescription(){return detailed_itemDescription;}
    }
}



