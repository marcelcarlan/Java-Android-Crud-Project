package com.carlanmarcel.books.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import static com.carlanmarcel.books.utilities.Constants.SERVICE_NAME;

public class ServiceIntent extends IntentService {


    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
    public ServiceIntent() {
        super(SERVICE_NAME);
    }


    @Override
    protected void onHandleIntent( Intent intent) {

    }
}
