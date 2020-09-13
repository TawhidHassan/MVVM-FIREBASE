package com.example.mvvmbanglapart2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmbanglapart2.model.SignInUser;
import com.example.mvvmbanglapart2.repository.SignInRepository;

public class SignInViewModel extends AndroidViewModel {

    private SignInRepository repository;
    public LiveData<SignInUser> checkAuthenticateLivedata;

    public SignInViewModel(@NonNull Application application) {
        super(application);

        repository=new SignInRepository();
    }

    public void checkAutheticate(){
        checkAuthenticateLivedata=repository.checkAuthenticationInFirebase();
    }
}
