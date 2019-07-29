package com.maxis7567.example;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.PhpLaravelError;
import com.maxis7567.msvolley.RequestQueueContainer;
import com.maxis7567.msvolley.Respond;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.ResponseError;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Api {
    public static void getBrandModel(Context context, Respond<BrandModel> DataListener, ResponseError<PhpLaravelError> errorRespondError, LocalError localError){
        HashMap<String,String> header=new HashMap<>();
        header.put("Accept", "application/json");
        JsonRequest<BrandModel,PhpLaravelError> getBrand=new JsonRequest<>(JsonRequest.Method.POST,
                "https://bejato.com/api/v1/login",header,
                BrandModel.class,PhpLaravelError.class
                , DataListener,errorRespondError,localError);
        RequestQueueContainer.add(context,getBrand,localError);
    }

}
