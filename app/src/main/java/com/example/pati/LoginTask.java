package com.example.pati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<String, Integer, Integer> {
    private Context context;
    public LoginTask(Context context) {
        this.context = context;
    }
    private static final String LOGIN_URL = "https://dummyjson.com/auth/login";
    private static final String TAG = "LoginTask";

    @Override
    protected Integer doInBackground(String... credentials) {
        if (credentials.length != 2) {
            return HttpURLConnection.HTTP_BAD_REQUEST;
        }

        String username = credentials[0];
        String password = credentials[1];


        int statusCode = -1;

        try {
            URL url = new URL(LOGIN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            //JSON request body
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);

            OutputStream os = conn.getOutputStream();
            os.write(jsonRequest.toString().getBytes());
            os.flush();
            os.close();

            statusCode = conn.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", jsonResponse.getString("email"));
                editor.putString("firstName", jsonResponse.getString("firstName"));
                editor.putString("lastName", jsonResponse.getString("lastName"));
                editor.apply();
            }

            conn.disconnect();

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return statusCode;
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        if (statusCode == HttpURLConnection.HTTP_OK) {
            Intent intent = new Intent(context, BottomNavigationActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(), "Giriş başarısız! Kullanıcı adı veya şifre hatalı.", Toast.LENGTH_SHORT).show();
        }
    }


}
