package com.example.pati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);

        NavController navController = navHostFragment.getNavController();


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_lost_animal) {
                navController.navigate(R.id.lostAnimalFragment);
                return true;
            } else if (item.getItemId() == R.id.action_sick_animal) {
                navController.navigate(R.id.sickAnimalFragment);
                return true;
            } else if (item.getItemId() == R.id.action_donation) {
                navController.navigate(R.id.donationFragment);
                return true;
            } else if (item.getItemId() == R.id.action_profile) {
                navController.navigate(R.id.profileFragment);
                return true;
            } else if (item.getItemId() == R.id.action_logout) {
                Intent intent = new Intent(BottomNavigationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else {
                return false;
            }
        });


    }
}