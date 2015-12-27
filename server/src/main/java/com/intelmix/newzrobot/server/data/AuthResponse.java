package com.intelmix.newzrobot.server.data;

//TODO: merge this and request
public class AuthResponse {
    private String token;
    private int resultCode;

    public AuthResponse(String token, int resultCode) {
        this.token = token;
        this.resultCode = resultCode;
    }

    public String getToken() {
        return this.token;
    }

    public int getResultCode () {
        return this.resultCode;
    }
}




