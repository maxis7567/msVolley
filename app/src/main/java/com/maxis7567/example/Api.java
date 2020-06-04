package com.maxis7567.example;

import android.content.Context;

import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RequestQueueContainer;
import com.maxis7567.msvolley.Respond;
import com.maxis7567.msvolley.ResponseError;


import java.util.HashMap;


public class Api {
    public static void getBrandModel(Context context, Respond<String> DataListener, ResponseError<String> errorRespondError, LocalError localError){
        HashMap<String,String> header=new HashMap<>();
        header.put("Accept", "application/json");
        JsonRequest<String,String> getBrand=new JsonRequest<>(context,JsonRequest.Method.POST,
                "https://bejato.com/api/v1/login",header,
                String.class,String.class
                , DataListener,errorRespondError,localError);
        RequestQueueContainer.add(context,getBrand,localError);
    }

}
