package com.ads.adsagram.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ads.adsagram.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class NewPostActivity extends AppCompatActivity {
    private ImageView imgNewPost;
    private EditText txtPostDetail;
    private Bitmap selectedImage;
    private Uri selectedImageUri;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        imgNewPost = findViewById(R.id.imgSelectPost);
        txtPostDetail= findViewById(R.id.txtPostDetail);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public void onSelectImagClicked(View view){
        if (ContextCompat.checkSelfPermission(NewPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NewPostActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},52);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,50);
        }
    }
    public void onPostClicked(View view){
        if (selectedImageUri==null || selectedImage==null){
            Toast.makeText(NewPostActivity.this,"Select image to be able to post",Toast.LENGTH_LONG);
        }else{
            String imageExtension = ".png";
            final String imagePath = firebaseAuth.getCurrentUser().getEmail()+"/"+ UUID.randomUUID() + imageExtension;
            storageReference.child(imagePath).putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Posted",Toast.LENGTH_LONG);
                    StorageReference downloadedReference = firebaseStorage.getReference(imagePath);
                    downloadedReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap<String,Object> postData = new HashMap<>();
                            postData.put("user_mail",firebaseAuth.getCurrentUser().getEmail());
                            postData.put("post_detail",txtPostDetail.getText().toString());
                            postData.put("image_uri",uri.toString());
                            postData.put("create_date", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("posts").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                        }
                    });

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==52 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,50);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==50 && resultCode==RESULT_OK && data!=null){
            try {
                if (Build.VERSION.SDK_INT>=28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),data.getData());
                    selectedImage = ImageDecoder.decodeBitmap(source);
                }else{
                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                }
                imgNewPost.setImageBitmap(selectedImage);
                selectedImageUri = data.getData();

            }catch (IOException ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
