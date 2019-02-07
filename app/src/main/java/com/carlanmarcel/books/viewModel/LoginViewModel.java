package com.carlanmarcel.books.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.carlanmarcel.books.model.User;
import com.carlanmarcel.books.service.MainService;

public class LoginViewModel extends AndroidViewModel {

    private MainService mainService;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mainService = MainService.getInstance(application.getApplicationContext());
    }

    public User getUserByUsername (String username){
        return mainService.getUserByUsername(username);
    }

    public Boolean loginUser(String mUsername, String mPassword) {
        return mainService.login(mUsername,mPassword);
    }
}
