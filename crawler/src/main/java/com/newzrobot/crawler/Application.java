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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Application {

    //TODO: better logging for info, warn and exceptions
    //TODO: use LogEntries if it does not need much overhead
    //TODO: Move all moving parts to a config filec
    public static void main(String[] args) throws java.lang.ClassNotFoundException,java.lang.InstantiationException, java.lang.IllegalAccessException,
           java.sql.SQLException, java.net.UnknownHostException,java.net.MalformedURLException,java.io.IOException, com.rometools.rome.io.FeedException  {

               MongoClient mongo = new MongoClient( "localhost" , 27017 );
               DB db = mongo.getDB("data"); 
               DBCollection feed_entry = db.getCollection("feed_entry");

               Class.forName("com.mysql.jdbc.Driver").newInstance();
               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/newzrobot?", "root", "ykstr_thisisroot_#$%_");
               Statement stmt = null;
               ResultSet rs = null;

               stmt = conn.createStatement();
               rs = stmt.executeQuery("SELECT * FROM feed_source");

               while (rs.next()) {
                   String title = rs.getString("title");

                   String link = rs.getString("uri");

                   saveFeed(feed_entry, link);


                   System.out.println(title);
               }

               stmt.close();


               System.out.println("Done!");






    }

    public static void saveFeed(DBCollection feed_entry, String link) throws com.rometools.rome.io.FeedException, java.io.IOException {

        //URL feedUrl = new URL("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss");
        URL feedUrl = new URL(link);

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
