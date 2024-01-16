package com.example.pati;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;

public class Donation extends Fragment implements View.OnLongClickListener{

    public Donation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        TextView btnDonate = view.findViewById(R.id.btnDonate);

        btnDonate.setOnLongClickListener(this);

        return view;
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d("DonationFragment", "Button long clicked");

        Uri webpage = Uri.parse("https://www.thkd.org.tr/bagis.php");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
            Log.d("DonationFragment", "Intent started successfully");
        } else {
            Log.e("DonationFragment", "No suitable app to handle the intent");
        }

        return true;
    }
}
