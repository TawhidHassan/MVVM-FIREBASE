package com.example.mvvmbanglapart2.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsertFragment extends Fragment {

    private CircleImageView insertImageView;
    private EditText insertNameEditText;
    private EditText insertPhoneEditText;
    private EditText insertEmailEditText;

    private Button saveButton;
    private static final int CAPTURE_PICCODE = 989;
    private Uri insertImageUri= null;


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

        /*Intent intent= new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CAPTURE_PICCODE);*/
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(getContext(),this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==getActivity().RESULT_OK){
                insertImageUri= result.getUri();
                insertImageView.setImageURI(insertImageUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        /* if(resultCode== getActivity().RESULT_OK){
            if(requestCode== CAPTURE_PICCODE){
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
               Bitmap resize= Bitmap.createScaledBitmap(bitmapImage,
                       (int) (bitmapImage.getWidth()*0.5),
                       (int) (bitmapImage.getHeight()*0.5),
                       true);
               insertImageUri= resize;
                    insertImageView.setImageBitmap(resize);
            }
        }*/

    }

    private void initialViewModel() {

    }
}