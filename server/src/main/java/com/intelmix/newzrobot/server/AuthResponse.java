package com.intelmix.newzrobot.server;

//TODO: merge this and request
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}




