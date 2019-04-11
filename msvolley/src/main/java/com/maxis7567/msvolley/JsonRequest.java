package com.maxis7567.msvolley;


import com.android.volley.NetworkResponse;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonRequest<T> extends Request<T> {
    private Gson gson = new Gson();
    private String body;
    private final Map<String, String> header;
    private Type type;
    private Response.Listener<T> responseLisener;

    public JsonRequest(int method, String url, Type type, Response.Listener<T> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.type = type;
        this.responseLisener = responseLisener;
        body = null;
        header = null;
    }

    public JsonRequest(int method, String url, String body, Type type, Response.Listener<T> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.body = body;
        this.type = type;
        this.responseLisener = responseLisener;
        header = null;
    }
    public JsonRequest(int method, String url, Map<String,String> header, Type type, Response.Listener<T> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.header = header;
        this.type = type;
        this.responseLisener = responseLisener;
    }
    public JsonRequest(int method, String url, String body, Map<String,String> header, Type type, Response.Listener<T> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.body = body;
        this.header = header;
        this.type = type;
        this.responseLisener = responseLisener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String stringResponse = new String(response.data, "UTF-8");
            T respone = gson.fromJson(stringResponse, type);
            return Response.success(respone, null);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        responseLisener.onResponse(response);
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
