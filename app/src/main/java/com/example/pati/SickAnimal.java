package com.example.pati;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SickAnimal extends Fragment {


    private RecyclerView recyclerView;
    private SickPostsAdapter sickPostsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sick_animal, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_sick_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        sickPostsAdapter = SickPostsAdapter.getInstance();
        recyclerView.setAdapter(sickPostsAdapter);

        Button addPostButton = view.findViewById(R.id.btn_add_sick_post);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_sickAnimalFragment_to_addPostSickAnimal);
            }
        });

        return view;
    }
}