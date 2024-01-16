package com.example.pati;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPostSickAnimal extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private Button btnAddSickPost;
    private EditText expView;
    private EditText addressView;
    private ImageView lostImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post_sick_animal, container, false);

        expView = view.findViewById(R.id.sick_exp_et);
        addressView = view.findViewById(R.id.sick_address_et);

        btnAddSickPost = view.findViewById(R.id.btn_add_sick_post_animal);
        lostImageView = view.findViewById(R.id.sickImageView);

        btnAddSickPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goGallery();
            }
        });

        Button postLostButton = view.findViewById(R.id.post_sick_animal);
        postLostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageUri != null) {
                    Bitmap selectedBitmap;
                    try {
                        selectedBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                        byte[] imageByteArray = getBitmapAsByteArray(selectedBitmap);
                        Post post = new Post(imageByteArray, expView.getText().toString(), addressView.getText().toString(), DatabaseHelper.getInstance(getActivity()).getLoggedInUsername());
                        DatabaseHelper.getInstance(getActivity()).addPost(post);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    SickPostsAdapter.getInstance().fetchPosts();
                    Navigation.findNavController(view).navigate(R.id.action_addPostSickAnimal_to_sickAnimalFragment);
                } else {
                    // Kullanıcı bir resim seçmediğinde yapılacak işlemler.
                    Toast.makeText(getContext().getApplicationContext(), "Please upload an image!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void goGallery() {
        Intent galeriIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galeriIntent.setType("image/*");
        startActivityForResult(galeriIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            Glide.with(requireContext())
                    .load(selectedImageUri)
                    .into(lostImageView);

        }
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}