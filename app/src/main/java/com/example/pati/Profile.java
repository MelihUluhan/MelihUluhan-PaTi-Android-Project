package com.example.pati;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Profile extends Fragment {

    private TextView fullNameTextView,userNameTextView,emailTextView;


private Button showHideButton;
    public Profile() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        fullNameTextView = view.findViewById(R.id.profile_fullName);
        userNameTextView = view.findViewById(R.id.profile_userName);
        emailTextView = view.findViewById(R.id.profile_email);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String firstName = sharedPreferences.getString("firstName", "");
        String lastName = sharedPreferences.getString("lastName", "");

        fullNameTextView.setText("Full Name: " + firstName+" " +lastName);
        userNameTextView.setText("Username: " + username);
        emailTextView.setText("Email: " + email);

        return view;
    }
}
