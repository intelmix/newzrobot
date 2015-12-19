package com.newzrobot.crawler;
//TODO: change package names to include intelmix

import com.rometools.rome.io.*;
import com.rometools.rome.feed.synd.*;
import java.net.URL;
import java.util.List;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.security.MessageDigest;


public class Application {

    //TODO: better logging for info, warn and exceptions
    //TODO: use LogEntries if it does not need much overhead
    //TODO: Move all moving parts to a config filec
    public static void main(String[] args) throws java.net.UnknownHostException,java.net.MalformedURLException,java.io.IOException, com.rometools.rome.io.FeedException  {

        /*                          List<String> dbs = mongo.getDatabaseNames();
                  for(String db : dbs){
                  System.out.println("Database: " + db);

                  }

                  DB db = mongo.getDB("data"); 
                  DBCollection table = db.getCollection("user");

                  BasicDBObject document = new BasicDBObject();
                  document.put("name", "mkyong");
                  document.put("age", 30);
                  table.insert(document);

                  return;
                  */
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        DB db = mongo.getDB("data"); 
        DBCollection feed_entry = db.getCollection("feed_entry");

        //URL feedUrl = new URL("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss");
        URL feedUrl = new URL("http://www.latimes.com/rss2.0.xml");

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedUrl));

        System.out.println("Feed Title: " + feed.getTitle());
        int counter=0;

        // Get the entry items...
        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            counter++;
            if ( counter > 3) break;

            String title = entry.getTitle();
            String uri = entry.getUri();
            Date date = entry.getPublishedDate();
            long epoch = date.getTime();
            String _id = md5(title + String.valueOf(epoch) + uri);

            BasicDBObject document = new BasicDBObject();
            document.put("_id", _id);
            document.put("t", title);
            document.put("u", uri);
            document.put("e", epoch);
            feed_entry.insert(document);

            System.out.println("GUID: " + _id);
            System.out.println("Title: " + title);
            System.out.println("Unique Identifier: " + uri);
            System.out.println("Published Date: " + epoch);


            System.out.println("");
        }

        System.out.println("Done!");
    }

    public static String md5(String base) {
        try{
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
