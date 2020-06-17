package com.maxis7567.msvolley;


import android.content.Context;
import android.util.Log;


import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;

import com.android.volley.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonRequest<T, E> extends Request<T> {

    private final Response<T> responseListener;
    private ResponseError<E> responseError;
    private LocalError localError;
    private Gson gson = new Gson();
    private String body;
    private Map<String, String> header;
    private Type type;
    private Type errType;

    /**
     * Supported request methods.
     */
    public static int DEPRECATED_GET_OR_POST = -1;
    public static int GET = 0;
    public static int POST = 1;
    public static int PUT = 2;
    public static int DELETE = 3;
    public static int HEAD = 4;
    public static int OPTIONS = 5;
    public static int TRACE = 6;
    public static int PATCH = 7;

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        body = null;
        header = null;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, String body, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        header = null;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, body = [" + body + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, Map<String, String> header, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }        RequestQueueContainer.add(context, this, localError);
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, header = [" + header + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, String body, Map<String, String> header, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, body = [" + body + "]\n, header = [" + header + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    @Override
    protected com.android.volley.Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String stringResponse = new String(response.data, "UTF-8");
            Log.d("Api Response", "***********\n" + stringResponse);
            T respond;
            if (type == String.class) {
                respond = (T) stringResponse;
                return com.android.volley.Response.success(respond, null);
            } else {
                respond = gson.fromJson(stringResponse, type);
                return com.android.volley.Response.success(respond, null);
            }

        } catch (JsonParseException | UnsupportedEncodingException e) {
            localError.error(e.toString());
            return null;
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            String stringResponse;
            Log.d("Api Response", "***********\n" + new String(volleyError.networkResponse.data));
            try {
                stringResponse = new String(volleyError.networkResponse.data, "UTF-8");
                E respond;
                if (errType == String.class) {
                    respond = (E) stringResponse;
                    RespondError<E> error = new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode, respond);
                    responseError.error(error);
                } else {
                    respond = gson.fromJson(stringResponse, errType);
                    RespondError<E> error = new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode, respond);
                    responseError.error(error);
                }
            } catch (JsonParseException | UnsupportedEncodingException e) {
                localError.error(e.toString());
            }


        } else {
            if (volleyError instanceof TimeoutError){
                localError.error("TimeoutError");
            }else if (volleyError.getMessage()!=null){
                localError.error(volleyError.getMessage());
            }else
            localError.error("network not available");
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        if (response != null) {
            responseListener.respond(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (header == null) {
            return super.getHeaders();
        } else
            return header;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (body == null) {
            return super.getBody();
        } else
            return body.getBytes();

    }


}
