package com.maxis7567.msvolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class RequestQueueContainer {
    private static RequestQueue requestQueueContainer;
    private static RequestQueue getRequestQueueContainer(Context context){
        if (requestQueueContainer==null){
            requestQueueContainer= Volley.newRequestQueue(context);
        }
        return requestQueueContainer;
    }
    public static void removeFromQueue(Context context,Object tag){
        getRequestQueueContainer(context).cancelAll(tag);
    }
    public static void add(Context context, Request jsonRequest, LocalError localError){
        if (NetworkCheck.isNetworkAvailable(context)){
            getRequestQueueContainer(context).add(jsonRequest);
        }else {
            localError.error("network not available");
        }
    }
    public static void addSingleInstance(Context context, Request jsonRequest, LocalError localError){
        if (NetworkCheck.isNetworkAvailable(context)){
            removeFromQueue(context,jsonRequest.getTag().toString());
            getRequestQueueContainer(context).add(jsonRequest);
        }else {
            localError.error("network not available");
        }
    }


}
