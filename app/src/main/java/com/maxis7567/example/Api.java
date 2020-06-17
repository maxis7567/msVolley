package com.maxis7567.example;

import android.content.Context;

import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.maxis7567.msvolley.RetryPolicy;


import java.util.HashMap;


public class Api {
    public static void getBrandModel(Context context, Response<String> DataListener, ResponseError<String> errorResponseError, LocalError localError) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        JsonRequest<String, String> getBrand = new JsonRequest<>(context, JsonRequest.POST,
                new RetryPolicy(5000,0,0)
                ,"http://10.0.2.2:8080", header,
                String.class, String.class
                , DataListener, errorResponseError, localError);
    }

}
