package com.maxis7567.msvolley.uploder;

import android.app.Application;

import net.gotev.uploadservice.BuildConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.okhttp.OkHttpStack;

import okhttp3.OkHttpClient;

public class Initializer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "com.yourcompany.yourapp";
        OkHttpClient client = new OkHttpClient(); // create your own OkHttp client
        UploadService.HTTP_STACK = new OkHttpStack(client); // make the library use your own OkHttp client
    }
}