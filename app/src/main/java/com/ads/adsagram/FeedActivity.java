package com.ads.adsagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menuSingOut){
            new AlertDialog.Builder(FeedActivity.this)
                    .setTitle("SingOut")
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setMessage("Are you sure you want to sing out?")
                    .setPositiveButton("Yes, Sing Out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firebaseAuth.signOut();
                            Intent intentLogin = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intentLogin);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }else if (item.getItemId()==R.id.menuNewPost){
            Intent intentNewPost = new Intent(getApplicationContext(),NewPostActivity.class);
            startActivity(intentNewPost);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.adsagram_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
