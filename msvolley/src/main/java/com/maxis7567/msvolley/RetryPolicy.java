package com.maxis7567.msvolley;

import com.android.volley.DefaultRetryPolicy;

public class RetryPolicy
{
    private int timeoutMs = 2500;

    private int maxRetries = 1;

    private float backoffMulti;

    public RetryPolicy(int timeoutMs, int maxRetries, float backoffMulti) {
        this.timeoutMs = timeoutMs;
        this.maxRetries = maxRetries;
        this.backoffMulti = backoffMulti;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public float getBackoffMulti() {
        return backoffMulti;
    }

    public void setBackoffMulti(float backoffMulti) {
        this.backoffMulti = backoffMulti;
    }
}
