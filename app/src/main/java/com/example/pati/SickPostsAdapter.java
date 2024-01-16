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

public class SickPostsAdapter extends RecyclerView.Adapter<SickPostsAdapter.PostViewHolder> {

    private static SickPostsAdapter _adapter;

    private SickPostsAdapter() {
    }

    static SickPostsAdapter getInstance() {
        if (_adapter == null) {
            _adapter = new SickPostsAdapter();
        }
        return _adapter;
    }

    private ArrayList<Post> posts = DatabaseHelper.getInstance(null).getAllPosts(false);

    public void fetchPosts() {
        posts = DatabaseHelper.getInstance(null).getAllPosts(false);
        notifyDataSetChanged();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView sickTextViewAddress;
        TextView sickTextViewExp;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sickimageView);
            sickTextViewAddress = itemView.findViewById(R.id.sickTextViewAddress);
            sickTextViewExp = itemView.findViewById(R.id.sickTextViewExp);
        }
    }


    @NonNull
    @Override
    public SickPostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posted_sick_animal_list, parent, false);
        return new SickPostsAdapter.PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SickPostsAdapter.PostViewHolder holder, int position) {
        Post sickPost = posts.get(position);

        Bitmap imageBitmap = BitmapFactory.decodeByteArray(sickPost.image, 0, sickPost.image.length);

        holder.imageView.setImageBitmap(imageBitmap);
        holder.sickTextViewAddress.setText(sickPost.address);
        holder.sickTextViewExp.setText(sickPost.explanation);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
