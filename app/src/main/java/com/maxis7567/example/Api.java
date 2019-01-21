package com.maxis7567.example;

import android.content.Context;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.RequestQueueContainer;

import java.util.ArrayList;
import java.util.List;


public class Api {
    public static void getBrandModel(Context context, Response.Listener<List<BrandModel>> DataListener, Response.ErrorListener errorListener){
        JsonRequest<List<BrandModel>> getBrand=new JsonRequest<>(JsonRequest.Method.GET,
                "https://my.api.mockaroo.com/carsbrand.json?key=",
                new TypeToken<ArrayList<BrandModel>>(){}.getType(),
                DataListener,
                errorListener);
        RequestQueueContainer.getRequestQueueContainer(context).add(getBrand);
    }

}
