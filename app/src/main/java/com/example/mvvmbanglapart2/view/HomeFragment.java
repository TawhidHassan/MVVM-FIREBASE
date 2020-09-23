package com.example.mvvmbanglapart2.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvvmbanglapart2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private Button signOutButton;
    private CircleImageView profileImageView;
    private TextView nameTextView,emailTextView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //find section
        signOutButton= view.findViewById(R.id.signOutButtonId);
        profileImageView= view.findViewById(R.id.profileImageId);
        nameTextView= view.findViewById(R.id.profileNameId);
        emailTextView= view.findViewById(R.id.profileEmailId);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
            }
        });

    }
}