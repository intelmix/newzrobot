package com.intelmix.newzrobot.server.data;

import redis.clients.jedis.Jedis;

public class RedisUser {
    private Jedis jedis = new Jedis("localhost");

    private String id;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
    private String gender;
    private String locale;
    private String email;
    private String key;

    public RedisUser(String email) {
        this.email = email;
        key = "users::"+email;
    }

    public boolean exists() {
         String id = jedis.hget(key, "id");

         return ( id != null );
    }

    private String makeNotNull(String x) {
         if ( x == null ) return "";

         return x;
    }


    public void initialize(APIUser user) {
         jedis.hset(key, "id", makeNotNull(user.getId()));
         jedis.hset(key, "name", makeNotNull(user.getName()));
         jedis.hset(key, "givenName", makeNotNull(user.getGivenName()));
         jedis.hset(key, "familyName", makeNotNull(user.getFamilyName()));
         jedis.hset(key, "link", makeNotNull(user.getLink()));
         jedis.hset(key, "picture", makeNotNull(user.getPicture()));
         jedis.hset(key, "gender", makeNotNull(user.getGender()));
         jedis.hset(key, "locale", makeNotNull(user.getLocale()));
    }

    public String getEmail() {
        return this.email;
    }

    public String getId() {
        return jedis.hget(key, "id");
    }

    public String getName() {
        return jedis.hget(key, "name");
    }

    public String getGivenName() {
        return jedis.hget(key, "givenName");
    }

    public String getFamilyName() {
        return jedis.hget(key, "familyName");
    }

    public String getLink() {
        return jedis.hget(key, "link");
    }

    public String getPicture() {
        return jedis.hget(key, "picture");
    }

    public String  getGender() {
        return jedis.hget(key, "gender");
    }

    public String getLocale() {
        return jedis.hget(key, "locale");
    }

    public String getAuthToken() {
         return jedis.hget(key, "authToken");
    }

    public void setAuthToken(String token) {
         jedis.hset(key, "authToken", token);
    }
}
