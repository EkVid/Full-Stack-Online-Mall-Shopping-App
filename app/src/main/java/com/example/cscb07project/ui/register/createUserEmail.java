package com.example.cscb07project.ui.register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cscb07project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createUserEmail implements createUser{
    private String email;
    private String password;
    private String storeName;
    private int isOwner_check;
    private FirebaseAuth mAuth;
    private activity_register_contract.Model model;
    private activity_register_contract.Presenter presenter;
    public createUserEmail (String email, String password, String storeName, int isOwner_check,
                            FirebaseAuth mAuth, activity_register_contract.Model model,
                            activity_register_contract.Presenter presenter) {
        this.email = email;
        this.password = password;
        this.storeName = storeName;
        this.isOwner_check = isOwner_check;
        this.mAuth = mAuth;
        this.model = model;
        this.presenter = presenter;
    }
    @Override
    public void create() {

        // checking and uploading storeLogo
        if(model.getStoreLogoUri() == null) {
            model.doToastView("Upload store logo");
            return;
        }

        // creating the account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        presenter.changeProgressBarVisibility(4);

                        if (task.isSuccessful()) {
                            Log.d("TAG_REGISTER", "createUserWithEmail:success");

                            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().
                                    child("users").child(mAuth.getCurrentUser().getUid());
                            if (isOwner_check == R.id.radioButton_register_owner) {
                                dbReference.child("isOwner").setValue(true);
                                dbReference.child("storeName").setValue(storeName);
                                FirebaseDatabase.getInstance().getReference("stores/" + storeName + "/" + "logo")
                                        .setValue(presenter.setStoreLogo());

                                FirebaseDatabase.getInstance().getReference("stores/" + storeName + "/" + "owner")
                                        .setValue(mAuth.getUid());

//                                FirebaseDatabase.getInstance().getReference("stores/" + storeName + "/" + "owner")
//                                        .setValue(mAuth.getUid());

                            } else if (isOwner_check == R.id.radioButton_register_customer) {
                                dbReference.child("isOwner").setValue(false);
                                dbReference.child("cart").setValue("");
                            }
                        } else {
                            Log.w("TAG_REGISTER", "createUserWithEmail:failure");
                            model.doToastView("Authentication failed");
                        }
                    }
                });
    }


}
