package com.ads.adsagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentData;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsername,txtPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Intent lcIntent = new Intent(LoginActivity.this,FeedActivity.class);
            startActivity(lcIntent);
            finish();
        }
    }
    public void btnSingUpClicked(View view){
        final String lcUsername = txtUsername.getText().toString();
        final String lcPassword = txtPassword.getText().toString();
        if (lcUsername.isEmpty()||lcPassword.length()<5){
            Toast.makeText(LoginActivity.this,"Username is required amd password should contains min 5 characters.",Toast.LENGTH_LONG).show();
            return;
        }
         firebaseAuth.createUserWithEmailAndPassword(lcUsername,lcPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
             @Override
             public void onSuccess(AuthResult authResult) {
                 Toast.makeText(LoginActivity.this, "SUCCESS",Toast.LENGTH_LONG).show();
                 firebaseAuth.signInWithEmailAndPassword(lcUsername,lcPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {
                         Intent lcIntent = new Intent(LoginActivity.this,FeedActivity.class);
                         startActivity(lcIntent);
                         finish();
                     }
                 });
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                 finish();
             }
         });
    }
    public void btnLoginClicked(View view){
        final String lcUsername = txtUsername.getText().toString();
        final String lcPassword = txtPassword.getText().toString();
        if (lcUsername.isEmpty()||lcPassword.length()<5){
            Toast.makeText(LoginActivity.this,"Username is required amd password should contains min 5 characters.",Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(lcUsername,lcPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent lcIntent = new Intent(LoginActivity.this,FeedActivity.class);
                startActivity(lcIntent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
