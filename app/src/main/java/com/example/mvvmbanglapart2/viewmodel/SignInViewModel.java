package com.example.mvvmbanglapart2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmbanglapart2.model.SignInUser;
import com.example.mvvmbanglapart2.repository.SignInRepository;
import com.google.firebase.auth.AuthCredential;

public class SignInViewModel extends AndroidViewModel {

    private SignInRepository repository;
    public LiveData<SignInUser> checkAuthenticateLivedata;
    public LiveData<String> authenticateUserLiveData;

    public SignInViewModel(@NonNull Application application) {
        super(application);

        repository=new SignInRepository();
    }
    //firebase sign in with google
    public void signInWithGoogle(AuthCredential authCredential){
        authenticateUserLiveData = repository.firebaseSignInWithGoogle(authCredential);
    }


    public void checkAutheticate(){
        checkAuthenticateLivedata=repository.checkAuthenticationInFirebase();
    }
}
