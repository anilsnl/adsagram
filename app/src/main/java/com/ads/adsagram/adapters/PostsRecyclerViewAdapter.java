package com.ads.adsagram.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.adsagram.R;
import com.ads.adsagram.models.PostItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.os.FileUtils.copy;

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
