package com.example.pati;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LostPostsAdapter extends RecyclerView.Adapter<LostPostsAdapter.PostViewHolder> {
    private static LostPostsAdapter _adapter;

    private LostPostsAdapter() {
    }

    static LostPostsAdapter getInstance() {
        if (_adapter == null) {
            _adapter = new LostPostsAdapter();
        }
        return _adapter;
    }

    private ArrayList<Post> posts = DatabaseHelper.getInstance(null).getAllPosts(true);

    public void fetchPosts() {
        posts = DatabaseHelper.getInstance(null).getAllPosts(true);
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView lostTextViewAddress;
        TextView lostTextViewTel;
        TextView lostTextViewExp;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            lostTextViewAddress = itemView.findViewById(R.id.lostTextViewAddress);
            lostTextViewTel = itemView.findViewById(R.id.lostTextViewTel);
            lostTextViewExp = itemView.findViewById(R.id.lostTextViewExp);
        }
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posted_lost_animal_list, parent, false);
        return new PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post lostPost = posts.get(position);

        Bitmap imageBitmap = BitmapFactory.decodeByteArray(lostPost.image, 0, lostPost.image.length);

        holder.imageView.setImageBitmap(imageBitmap);
        holder.lostTextViewAddress.setText(lostPost.address);
        holder.lostTextViewTel.setText(lostPost.phoneNumber);
        holder.lostTextViewExp.setText(lostPost.explanation);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}

