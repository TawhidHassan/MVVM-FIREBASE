package com.example.mvvmbanglapart2.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvvmbanglapart2.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsertFragment extends Fragment {

    private CircleImageView insertImageView;
    private EditText insertNameEditText;
    private EditText insertPhoneEditText;
    private EditText insertEmailEditText;

    private Button saveButton;


    public InsertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialViewModel();

        //find section...
        insertImageView= view.findViewById(R.id.insertImageId);
        insertNameEditText= view.findViewById(R.id.insertNameId);
        insertPhoneEditText= view.findViewById(R.id.insertPhoneId);
        insertEmailEditText= view.findViewById(R.id.insertEmailId);
        saveButton= view.findViewById(R.id.saveButtonId);

        insertImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void uploadImage() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else {
                imagePick();
            }
        }
    }

    private void imagePick() {
    }

    private void initialViewModel() {

    }
}