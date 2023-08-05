package com.example.cscb07project.ui.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.ui.register.activity_register_contract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loggingInUserEmail implements loggingInUser{

    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private activity_login_contract.Presenter presenter;

    public loggingInUserEmail(String email, String password, FirebaseAuth mAuth,
                              activity_login_contract.Presenter presenter) {
        this.email = email;
        this.password = password;
        this.mAuth = mAuth;
        this.presenter = presenter;
    }

    @Override
    public void enter() {
        // Login to user in firebase Auth
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        presenter.changeProgressBarVisibility(4);
                        if (task.isSuccessful()) {
                            Log.d("TAG_LOGIN", "signInWithEmail:success");
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG_LOGIN", "signInWithEmail:failure", task.getException());
                            presenter.doToastView("Incorrect Email/Password");
                        }
                    }
                });
    }
}