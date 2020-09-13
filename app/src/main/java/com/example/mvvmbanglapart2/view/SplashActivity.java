package com.example.mvvmbanglapart2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.mvvmbanglapart2.model.SignInUser;
import com.example.mvvmbanglapart2.viewmodel.SignInViewModel;

public class SplashActivity extends AppCompatActivity {

    SignInViewModel signInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSpalashViewModel();
        checkIfUserAuthenticate();
    }

    private void checkIfUserAuthenticate() {
        signInViewModel.checkAutheticate();
        signInViewModel.checkAuthenticateLivedata.observe(this, new Observer<SignInUser>() {
            @Override
            public void onChanged(SignInUser signInUser) {
                if (!signInUser.isAuth)
                {
                    goToSignInActivity();
                }else
                {
                    gotoMainActivity();
                }
            }
        });
    }

    private void gotoMainActivity() {
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToSignInActivity() {
        Intent intent=new Intent(SplashActivity.this,SignInActivity.class);
        startActivity(intent);
        fileList();
    }

    private void initSpalashViewModel() {
        signInViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(SignInViewModel.class);
    }
}