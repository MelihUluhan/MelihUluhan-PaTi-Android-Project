package com.example.pati;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends Fragment {
    EditText userNameEt;
    EditText passwordEt;
    CheckBox rememberMeCheckBox;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userNameEt = view.findViewById(R.id.text_login);
        passwordEt = view.findViewById(R.id.text_password);
        rememberMeCheckBox = view.findViewById(R.id.remember_me_cb);

        sharedPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        loadSavedCredentials();

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = userNameEt.getText().toString().trim();
                String enteredPassword = passwordEt.getText().toString().trim();
                new LoginTask(getContext()).execute(enteredUsername, enteredPassword);

                if (rememberMeCheckBox.isChecked()) {
                    saveCredentials(enteredUsername, enteredPassword);
                } else {
                    clearSavedCredentials();
                }
            }
        });

        return view;
    }

    private void loadSavedCredentials() {
        String savedUserName = sharedPreferences.getString("userName", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean rememberMeChecked = sharedPreferences.getBoolean("rememberMeChecked", false);

        if (rememberMeChecked && !savedUserName.isEmpty() && !savedPassword.isEmpty()) {
            userNameEt.setText(savedUserName);
            passwordEt.setText(savedPassword);
            rememberMeCheckBox.setChecked(true);
        }
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", username);
        editor.putString("password", password);
        editor.putBoolean("rememberMeChecked", true);
        editor.apply();
    }

    private void clearSavedCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userName");
        editor.remove("password");
        editor.putBoolean("rememberMeChecked", false);
        editor.apply();

        userNameEt.setText("");
        passwordEt.setText("");
    }
}
