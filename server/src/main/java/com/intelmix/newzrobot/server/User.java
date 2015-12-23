package com.intelmix.newzrobot.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String id;
    private String name;

    @JsonProperty("given_name")
    private String given_name;

    @JsonProperty("family_name")
    private String family_name;

    private String link;
    private String picture;
    private String gender;
    private String locale;
    private String email;


    public User() {

    }

    public String getEmail() {
        return this.email;
    }


    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGivenName() {
        return this.given_name;
    }

    public String getFamilyName() {
        return this.family_name;
    }

    public String getLink() {
        return this.link;
    }

    public String getPicture() {
        return this.picture;
    }

    public String  getGender() {
        return this.gender;
    }

    public String getLocale() {
        return this.locale;
    }
}




