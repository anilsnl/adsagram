package com.ads.adsagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<PostItemModel> postItemModels;
    private RecyclerView recyclerView;
    PostsRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        postItemModels = new ArrayList<>();
        recyclerView = findViewById(R.id.rcyPosts);
        getData();
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

    public void getData(){
        CollectionReference collectionReference = firebaseFirestore.collection("posts");
        collectionReference.orderBy("create_date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG);
                } else if (queryDocumentSnapshots.getDocuments()!=null){
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                        PostItemModel model = new PostItemModel();
                        model.setUserMail(doc.getString("user_mail"));
                        model.setPostDetail(doc.getString("post_detail"));
                        model.setImageUrl(doc.getString("image_uri"));
                        postItemModels.add(model);
                    }
                    adapter = new PostsRecyclerViewAdapter(postItemModels);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }
}
