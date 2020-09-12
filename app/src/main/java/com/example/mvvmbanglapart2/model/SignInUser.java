package com.example.mvvmbanglapart2.model;

import java.io.Serializable;

public class SignInUser implements Serializable {

    public String uId;
    public String name;
    public String email;
    public String iamgeUrl;
    public boolean isAuth;

    public SignInUser() {
    }

    public SignInUser(String uId, String name, String email, String iamgeUrl) {
        this.uId = uId;
        this.name = name;
        this.email = email;
        this.iamgeUrl = iamgeUrl;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }
}
