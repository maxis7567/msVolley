package com.maxis7567.msvolley;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonRequest<T, E> extends Request<T> {

    private final Respond<T> responseListener;
    private ResponseError<E> responseError;
    private LocalError localError;
    private Gson gson = new Gson();
    private String body;
    private Map<String, String> header;
    private Type type;
    private Type errType;


    public JsonRequest( int method, String url, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        body = null;
        header = null;
        this.setRetryPolicy(new DefaultRetryPolicy(15000,3,2000));
        Log.d("Api Call", "JsonRequest() called with: method = [" + method + "]\n, url = [" + url + "]\n, type = [" + type + "]\n, errType = [" + errType + "]\n, responseListener = [" + responseListener + "], responseError = [" + responseError + "], localError = [" + localError + "]");
    }

    public JsonRequest( int method, String url, String body, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        header = null;
        this.setRetryPolicy(new DefaultRetryPolicy(15000,3,2000));
        Log.d("Api Call", "JsonRequest() called with: method = [" + method + "]\n, url = [" + url + "]\n, body = [" + body + "]\n, type = [" + type + "]\n, errType = [" + errType + "]\n, responseListener = [" + responseListener + "], responseError = [" + responseError + "], localError = [" + localError + "]");
    }
    public JsonRequest( int method, String url, Map<String, String> header, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        this.setRetryPolicy(new DefaultRetryPolicy(15000,3,2000));
        Log.d("Api Call", "JsonRequest() called with: method = [" + method + "]\n, url = [" + url + "]\n, header = [" + header + "]\n, type = [" + type + "]\n, errType = [" + errType + "]\n, responseListener = [" + responseListener + "], responseError = [" + responseError + "], localError = [" + localError + "]");
    }
    public JsonRequest( int method, String url, String body, Map<String, String> header, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        this.setRetryPolicy(new DefaultRetryPolicy(15000,3,2000));
        Log.d("Api Call", "JsonRequest() called with: method = [" + method + "]\n, url = [" + url + "]\n, body = [" + body + "]\n, header = [" + header + "]\n, type = [" + type + "]\n, errType = [" + errType + "]\n, responseListener = [" + responseListener + "], responseError = [" + responseError + "], localError = [" + localError + "]");
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String stringResponse = new String(response.data, "UTF-8");
            Log.d("Api Response", stringResponse);
            T respond;
            if (type==String.class){
                respond= (T) stringResponse;
                return Response.success(respond, null);
            }else {
                respond=gson.fromJson(stringResponse, type);
                return Response.success(respond, null);
            }

        } catch (JsonParseException | UnsupportedEncodingException e){
            localError.error(e.toString());
            return null;
        }
    }
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse!=null) {
            String stringResponse;
            Log.d("Api Response",new String(volleyError.networkResponse.data));
            try {
                stringResponse = new String(volleyError.networkResponse.data, "UTF-8");
                E respond;
                if (errType==String.class){
                    respond= (E) stringResponse;
                    RespondError<E> error= new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode,respond);
                    responseError.error(error);
                }else {
                    respond=gson.fromJson(stringResponse, errType);
                    RespondError<E> error= new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode,respond);
                    responseError.error(error);
                }
            } catch (JsonParseException | UnsupportedEncodingException e){
                localError.error(e.toString());
            }


        }else {
            localError.error("network not available");
        }
        return null;
    }
    @Override
    protected void deliverResponse(T response) {
        if (response!=null) {
            responseListener.respond(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(header==null){
            return super.getHeaders();
        }else
            return header;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(body==null) {
            return super.getBody();
        }else
            return body.getBytes();

    }



}
