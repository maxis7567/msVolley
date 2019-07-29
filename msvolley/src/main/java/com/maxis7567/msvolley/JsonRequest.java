package com.maxis7567.msvolley;


import android.content.Context;

import com.android.volley.NetworkResponse;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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


    public JsonRequest(int method, String url, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError,LocalError localError) {
        super(method, url, null);
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        body = null;
        header = null;
    }

    public JsonRequest(int method, String url, String body, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError,LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        header = null;
    }
    public JsonRequest(int method, String url, Map<String, String> header, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError,LocalError localError) {
        super(method, url, null);
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
    }
    public JsonRequest(int method, String url, String body, Map<String, String> header, Type type, Type errType, Respond<T> responseListener, ResponseError<E> responseError,LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String stringResponse = new String(response.data, "UTF-8");
            T respone = gson.fromJson(stringResponse, type);
            return Response.success(respone, null);
        } catch (UnsupportedEncodingException e) {
            localError.error(e.toString());
            return null;
        }catch (JsonParseException e){
            localError.error(e.toString());
            return null;
        }
    }
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse!=null) {
            String stringResponse = null;
            try {
                stringResponse = new String(volleyError.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                localError.error(e.getMessage());
            }
            E respone = gson.fromJson(stringResponse, errType);
            RespondError<E> error= new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode,respone);
            responseError.error(error);
        }else {
            localError.error(volleyError.toString());
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
