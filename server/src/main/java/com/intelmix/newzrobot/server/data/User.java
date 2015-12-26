package com.intelmix.newzrobot.server.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import redis.clients.jedis.Jedis;

public class User {
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
    private String redisKey;

    public User(String email) {
        this.email = email;
        this.redisKey = "users::" + email;
    }

    public bool exists() {
         String id = jedis.hget(redisKey, "id");

         return ( id != null );
    }

    public void init(String id, String name, String givenName, String familyName, 
            String link, String picture, String gender, String locale) {
         jedis.hset(redisKey, "id", id);
         jedis.hset(redisKey, "name", name);
         jedis.hset(redisKey, "givenName", givenName);
         jedis.hset(redisKey, "familyName", familyName);
         jedis.hset(redisKey, "link", link);
         jedis.hset(redisKey, "picture", picture);
         jedis.hset(redisKey, "gender", gender);
         jedis.hset(redisKey, "locale", locale);
    }

    public String getEmail() {
        return this.email;
    }

    public String getId() {
        return jedis.hget(redisKey, "id");
    }

    public String getName() {
        return jedis.hget(redisKey, "name");
    }

    public String getGivenName() {
        return jedis.hget(redisKey, "givenName");
    }

    public String getFamilyName() {
        return jedis.hget(redisKey, "familyName");
    }

    public String getLink() {
        return jedis.hget(redisKey, "link");
    }

    public String getPicture() {
        return jedis.hget(redisKey, "picture");
    }

    public String  getGender() {
        return jedis.hget(redisKey, "gender");
    }

    public String getLocale() {
        return jedis.hget(redisKey, "locale");
    }
}




