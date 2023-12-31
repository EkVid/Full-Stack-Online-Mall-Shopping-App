package com.example.cscb07project.ui.shop.OwnerList;

import static com.example.cscb07project.MainActivity.currentUser;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.ui.shop.ItemList.ItemListEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class OwnerListModel {

    private static final String LOGO_NODE_NAME = "image";
    private static final String PRICE_NODE_NAME = "price";
    private static final String BRAND = "brand";

    private static final String ForSale = "forSale";

    private static final String DESCRIP = "description";


    //private FirebaseAuth mAuth;
    //private FirebaseUser mUser;


    private final OwnerListPresenter presenter;
    private DatabaseReference query_owner;
    private ChildEventListener listener;

    private List<ItemListEntry> ItemsList;


    public OwnerListModel(OwnerListPresenter presenter, String url) {
        this.presenter = presenter;
        ItemsList = new ArrayList<>();
        FirebaseDatabase db = FirebaseDatabase.getInstance(url);
    }

    public void createEventListener() {
        //        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        String username = mUser.getUid();
        String username = currentUser;
                    getStoreName getStoreNameInstance = new getStoreName(username);
                    getStoreNameInstance.retrieveStoreName(new getStoreName.OnStoreNameListener() { // get storename
                        @Override
                        public void onStoreNameRetrieved(String storeName) {
                            query_owner = FirebaseDatabase.getInstance("https://grocery-store-app-75a7a-default-rtdb.firebaseio.com/").getReference().child("stores").child(storeName).child("items");
                            listener = query_owner.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                   boolean forSale = snapshot.child(ForSale).getValue(Boolean.class);
                                    if (forSale) {
                                        String itemName = snapshot.getKey();
                                        String logoURL = snapshot.child(LOGO_NODE_NAME).getValue(String.class);
                                        if (logoURL != null && !logoURL.isEmpty()) {
                                            FirebaseStorage storage = FirebaseStorage.getInstance();
                                            StorageReference storageRef = storage.getReference();
                                            StorageReference fileRef = storageRef.child(logoURL);

                                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String downloadUrl = uri.toString();
                                                    double price = snapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                                                    String brand = snapshot.child(BRAND).getValue(String.class);
                                                    String description = snapshot.child(DESCRIP).getValue(String.class);
                                                    ItemListEntry newEntry = new ItemListEntry(itemName, downloadUrl, price, brand, description);
                                                    ItemsList.add(newEntry);
                                                    presenter.setAdapter(ItemsList);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    Log.e("SLM.java", "Error getting download URL: " + exception.getMessage());
                                                }
                                            });
                                        } else {
                                            double price = snapshot.child(PRICE_NODE_NAME).getValue(Double.class);
                                            String brand = snapshot.child(BRAND).getValue(String.class);
                                            String description = snapshot.child(DESCRIP).getValue(String.class);
                                            ItemListEntry newEntry = new ItemListEntry(itemName, logoURL, price, brand, description);
                                            ItemsList.add(newEntry);
                                            presenter.setAdapter(ItemsList);
                                        }
                                    } else{
                                    }
                                }
                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    boolean forSale = snapshot.child(ForSale).getValue(Boolean.class);
                                    if (!forSale) {
                                        String itemName = snapshot.getKey();
                                        // Find and remove the item from the ItemsList
                                        for (ItemListEntry entry : ItemsList) {
                                            if (entry.getItemName().equals(itemName)) {
                                                ItemsList.remove(entry);
                                                break;
                                            }
                                        }
                                        presenter.setAdapter(ItemsList);
                                    }
                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                    String storeName = snapshot.getKey();
                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    onChildChanged(snapshot, previousChildName);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.d("SLM.java", "Err listening to item names");
                                    destroyEventListener();
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Log.d("store cannot get", "cannot get storename");
                        }
                    });
                }


    public void destroyEventListener() {
        if (listener != null) {
            query_owner.removeEventListener(listener);
        }
    }

    public List<ItemListEntry> getItemsList(){
        return ItemsList;
    }


}
