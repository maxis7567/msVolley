package com.maxis7567.msvolley;

public class RespondError<E> {
    private String message;
    private int resCode;
    private E respond;

    public String getMessage() {
        return message;
    }

    public int getResCode() {
        return resCode;
    }

    public E getRespond() {
        return respond;
    }

    public RespondError(String message, int resCode, E respond) {
        this.message = message;
        this.resCode = resCode;
        this.respond = respond;
    }
}
