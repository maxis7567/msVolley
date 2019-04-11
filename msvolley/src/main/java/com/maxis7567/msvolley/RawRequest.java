package com.maxis7567.msvolley;


import com.android.volley.NetworkResponse;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class RawRequest<String> extends Request<String> {
    private java.lang.String body;
    private final Map<java.lang.String, java.lang.String> header;
    private Map<java.lang.String, java.lang.String> param;
    private Response.Listener<String> responseLisener;


    public RawRequest(int method, java.lang.String url, Response.Listener<String> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.responseLisener = responseLisener;
        body = null;
        header = null;
    }

    public RawRequest(int method, java.lang.String url, java.lang.String body, Response.Listener<String> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.body = body;
        this.responseLisener = responseLisener;
        header = null;
    }
    public RawRequest(int method, java.lang.String url, Map<java.lang.String, java.lang.String> header, Response.Listener<String> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.header = header;
        this.responseLisener = responseLisener;
    }
    public RawRequest(int method, java.lang.String url, java.lang.String body, Map<java.lang.String, java.lang.String> header, Response.Listener<String> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.body = body;
        this.header = header;
        this.responseLisener = responseLisener;
    }
    public RawRequest(int method, java.lang.String url, Map<java.lang.String, java.lang.String> param, Map<java.lang.String, java.lang.String> header, Response.Listener<String> responseLisener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.param=param;
        this.header = header;
        this.responseLisener = responseLisener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String stringResponse = (String) new java.lang.String(response.data, "UTF-8");
            String respone = stringResponse;
            return Response.success(respone, null);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        responseLisener.onResponse(response);
    }

    @Override
    public Map<java.lang.String, java.lang.String> getHeaders() throws AuthFailureError {
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
