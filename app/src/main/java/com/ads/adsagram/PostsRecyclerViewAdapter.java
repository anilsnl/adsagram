package com.ads.adsagram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static android.os.FileUtils.copy;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.PostRecyclerViewViewHolder> {
    private ArrayList<PostItemModel> postItemModels;
    public  PostsRecyclerViewAdapter(ArrayList<PostItemModel> models){
        postItemModels = models;
    }
    @NonNull
    @Override
    public PostRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.posts_recyclerview_item,parent,false);
        return new PostRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecyclerViewViewHolder holder, int position) {
        holder.postMail.setText(postItemModels.get(position).getUserMail());
        holder.postDetail.setText(postItemModels.get(position).getPostDetail());
        Picasso.get().load(postItemModels.get(position).getImageUrl()).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return postItemModels.size();
    }


    class PostRecyclerViewViewHolder extends RecyclerView.ViewHolder{
        ImageView postImage;
        TextView postMail;
        TextView postDetail;
        public PostRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.posts_rcl_item_img);
            postMail = itemView.findViewById(R.id.posts_rcl_item_mail);
            postDetail = itemView.findViewById(R.id.posts_rcl_item_detail);

        }
    }
}
