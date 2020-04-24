package com.smartwash.smartwashagent;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;


import co.paystack.android.PaystackSdk;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        PaystackSdk.initialize(getApplicationContext());

        Map configCloudinary = new HashMap();
        configCloudinary.put("cloud_name", "faithmodel");
        configCloudinary.put("api_key", "553666973284922");
        configCloudinary.put("api_secret", "3Zqn7aNuh8Wa-MbiljwCfUnWiLM");
        configCloudinary.put("upload_preset", "kfaatqjb");
        MediaManager.init(this, configCloudinary);


    }
}
