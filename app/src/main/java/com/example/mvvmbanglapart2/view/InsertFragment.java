package com.example.mvvmbanglapart2.view;

import android.Manifest;
import android.app.AlertDialog;
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
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvvmbanglapart2.R;
import com.example.mvvmbanglapart2.model.ContactUser;
import com.example.mvvmbanglapart2.viewmodel.ContactViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class InsertFragment extends Fragment {

    private CircleImageView insertImageView;
    private EditText insertNameEditText;
    private EditText insertPhoneEditText;
    private EditText insertEmailEditText;

    private Button saveButton;
    private static final int CAPTURE_PICCODE = 989;
    private Uri insertImageUri= null;

    private ContactViewModel contactViewModel;

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
                String id= randomDigit();
                String name= insertNameEditText.getText().toString();
                String phone= insertPhoneEditText.getText().toString();
                String email= insertEmailEditText.getText().toString();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email) && insertImageUri != null){
                    //show spots dialogue here.....
                    final AlertDialog dialogue= new SpotsDialog.Builder().setContext(getActivity()).setTheme(R.style.Custom).setCancelable(true).build();
                    dialogue.show();

                    ContactUser user= new ContactUser(id,name,"image_uri",phone,email);
                    contactViewModel.insert(user,insertImageUri);
                    contactViewModel.insertResultLiveData.observe(getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            dialogue.dismiss();
                            Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
                        }
                    });

                    insertImageView.setImageResource(R.drawable.profile);
                    insertNameEditText.setText("");
                    insertPhoneEditText.setText("");
                    insertEmailEditText.setText("");


                }
                else {
                    Toast.makeText(getActivity(), "Please Fill up all field", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initialViewModel() {

    }

    //generate a random digit.........
    private String randomDigit() {

        char[] chars= "1234567890".toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        Random random= new Random();
        for(int i=0;i<4;i++){
            char c= chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();


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


}