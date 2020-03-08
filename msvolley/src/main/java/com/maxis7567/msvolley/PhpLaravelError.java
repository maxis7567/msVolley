package com.maxis7567.msvolley;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class PhpLaravelError {
    @SerializedName("message")
    private String message;
    @SerializedName("errors")
    private JsonObject errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public void setErrors(JsonObject errors) {
        this.errors = errors;
    }
}
