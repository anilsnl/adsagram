package com.ads.adsagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    }
    public void btnSingUpClicked(View view){
        String lcUsername = txtUsername.getText().toString();
        String lcPassword = txtPassword.getText().toString();

         firebaseAuth.createUserWithEmailAndPassword(lcUsername,lcPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
             @Override
             public void onSuccess(AuthResult authResult) {
                 Toast.makeText(LoginActivity.this, "SUCCESS",Toast.LENGTH_LONG).show();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(LoginActivity.this, e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
             }
         });
    }
    public void btnLoginClicked(View view){

    }
}
