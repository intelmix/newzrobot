package newzrobot;

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

    /*public User(String id, String name, String given_name, String family_name, String link, String picture, String gender, String locale) {
        this.id = id;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.link = link;
        this.picture = picture;
        this.gender = gender;
        this.locale = locale;
    }*/

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




