package com.example.pati;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LostAnimal extends Fragment {

    private RecyclerView recyclerView;
    private LostPostsAdapter lostPostsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_animal, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_lost_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        lostPostsAdapter = LostPostsAdapter.getInstance();
        recyclerView.setAdapter(lostPostsAdapter);

        Button addPostButton = view.findViewById(R.id.btn_add_lost_post);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_lostAnimalFragment_to_addPostLostAnimal);
            }
        });

        return view;
    }
}



