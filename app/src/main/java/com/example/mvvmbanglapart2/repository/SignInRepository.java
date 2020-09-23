package com.example.mvvmbanglapart2.repository;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmbanglapart2.model.SignInUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInRepository {
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private SignInUser user=new SignInUser();

    public MutableLiveData<SignInUser> checkAuthenticationInFirebase()
    {
        MutableLiveData<SignInUser> isAuthenticateLiveData=new MutableLiveData<>();

        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser==null)
        {
            user.isAuth=false;
            isAuthenticateLiveData.setValue(user);
        }else
        {
            user.uId=currentUser.getUid();
            user.isAuth=true;
            isAuthenticateLiveData.setValue(user);
        }

        return isAuthenticateLiveData;
    }



    public MutableLiveData<String>firebaseSignInWithGoogle(AuthCredential authCredential)
    {
        final MutableLiveData<String> authMutableLiveData=new MutableLiveData<>();

        firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                String uId=currentUser.getUid();
                authMutableLiveData.setValue(uId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authMutableLiveData.setValue(e.toString());
            }
        });

        return authMutableLiveData;

    }


    //collect user info
    public MutableLiveData<SignInUser> collectUserData()
    {
        MutableLiveData<SignInUser> collectMutableLiveData=new MutableLiveData<>();

        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser!=null)
        {
            String uId=currentUser.getUid();
            String name=currentUser.getDisplayName();
            String email=currentUser.getEmail();
            Uri getImageUrl=currentUser.getPhotoUrl();
            String imageUri=getImageUrl.toString();

            SignInUser signInUser=new SignInUser(uId,name,email,imageUri);
            collectMutableLiveData.setValue(signInUser);

        }

        return collectMutableLiveData;
    }



}
