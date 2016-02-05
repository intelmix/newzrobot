package com.intelmix.newzrobot.crawler;

import com.rometools.rome.io.*;
import com.rometools.rome.feed.synd.*;
import java.net.URL;
import java.util.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.security.MessageDigest;
import redis.clients.jedis.Jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.Banner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

//*
//This ia a feed crawler class which reads a list of feeds rom application configuration file and crawls their feed data
//stores feed items in MongoDB while storing latest news in Redis
@Service
public class FeedCrawler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

    @Autowired
    private FeedConfig cfg;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private Environment env;

    @Value("${mongodb.server}")
    private String mongodbServer;

    @Value("${mongodb.port}")
    private int mongodbPort;

    Jedis jedis;
    /**
     * This method crawls in the feeds specified in application.yaml file and stores results in MongoDB.
     */
    public void doCrawl() {

        try {
            MongoClient mongo = new MongoClient( mongodbServer , mongodbPort );
            DB db = mongo.getDB("data"); 
            DBCollection feed_entry = db.getCollection("feed_entry");
            jedis = new Jedis("localhost");

            Set<String> feedIds = cfg.getFeedIds();

            for(String id: feedIds) {
                String link = cfg.getFeeds().get(id);

                long readCount = -1;
                
                try {
                    readCount = saveFeed(feed_entry, link, id);
                }
                catch (Exception e) {
                    logger.error("Error reading feed: "+id+" : "+e.getMessage());
                }
                
                if ( readCount != -1 ) {
                    String current = jedis.get("news_count");
                    if ( current == null ) current = "0";
                    Long news_count = Long.valueOf(current);
                    news_count += readCount;
                    jedis.set("news_count", news_count.toString());
                }
            }
        } 
        catch (Exception e) {
            logger.error("ERROR " + e.getMessage());
        }
    }


    /**
     * Saves items of the given RSS feed into MongoDB. 
     */
    private long saveFeed(DBCollection feed_entry, String link, String source_id) throws com.rometools.rome.io.FeedException, java.io.IOException {
        logger.info(source_id + ": Processing...");
        URL feedUrl = new URL(link);

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        input.setAllowDoctypes(true);

        int counter=0;

        // Get the entry items...
        for (SyndEntry entry : feed.getEntries()) {

            String title = entry.getTitle();
            String uri = entry.getUri();
            Date date = entry.getPublishedDate();
            long epoch = date.getTime();
            String _id = md5(title + String.valueOf(epoch) + uri);

            //check for duplicate id
            BasicDBObject query = new BasicDBObject("_id", _id);
            if ( feed_entry.findOne(query) == null ) {
                BasicDBObject document = new BasicDBObject();
                document.put("_id", _id);
                document.put("txt", title);
                document.put("uri", uri);
                document.put("epo", epoch);
                document.put("src", source_id);
                document.put("x", 0);
                feed_entry.insert(document);
                counter++;

                jedis.zadd("latest_news", epoch, "");
            }
        }

        //clear old items from redis's "latest_news"
        //remove items with lowest epoch from oldest one to the one
        //at index 50 from highest one
        jedis.zremrangeByScore("latest_news", 0, -50);

        logger.info(String.format("%s: Got %d feed items saved into database (for %s).", source_id, counter, link));

        return counter;
    }

    /**
     * Calculates Hash of the given string. This hash will be used to detect duplicate news items
     */
    private String md5(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();


            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}

