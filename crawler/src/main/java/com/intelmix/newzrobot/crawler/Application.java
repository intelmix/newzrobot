package com.intelmix.newzrobot.crawler;
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

//TODO: make this class more tidy, not everything should be statis, move almost all code to their respective classes
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
    public class Application implements CommandLineRunner {
        static Jedis jedis;
        private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

        public static void main(String[] args) {
            SpringApplication application = new SpringApplication(
                    Application.class);
            application.setWebEnvironment(false);
            application.setBannerMode(Banner.Mode.OFF);
            application.run(args);
        }

        //TODO: better logging for info, warn and exceptions
        //TODO: use LogEntries if it does not need much overhead
        //TODO: Move all moving parts to a config filec
        @Override
        public void run(String... args) {
            try {
                logger.info("begin of run");
                MongoClient mongo = new MongoClient( "localhost" , 27017 );
                DB db = mongo.getDB("data"); 
                DBCollection feed_entry = db.getCollection("feed_entry");
                jedis = new Jedis("localhost");

                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/newzrobot?", "root", "ykstr_thisisroot_#$%_");
                Statement stmt = null;
                ResultSet rs = null;

                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM feed_source");

                while (rs.next()) {
                    String title = rs.getString("title");

                    String link = rs.getString("uri");

                    saveFeed(feed_entry, link, rs.getString("guid"));

                    logger.info(title);
                }

                stmt.close();

                logger.info("Done!"); 
            } 
            catch (Exception e) {
                logger.error("ERROR " + e.getMessage());
            }


        }

        public void saveFeed(DBCollection feed_entry, String link, String source_id) throws com.rometools.rome.io.FeedException, java.io.IOException {

            //URL feedUrl = new URL("https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss");
            URL feedUrl = new URL(link);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            logger.info("Feed Title: " + feed.getTitle());
            int counter=0;

            // Get the entry items...
            for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
                counter++;

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
                document.put("s", source_id);
                document.put("x", 0);
                feed_entry.insert(document);

                logger.info("GUID: " + _id);
                logger.info("Title: " + title);
                logger.info("Unique Identifier: " + uri);
                logger.info("Published Date: " + epoch);


                logger.info("");
            }

            String current = jedis.get("news_count");
            if ( current == null ) current = "0";
            Integer news_count = Integer.valueOf(current);
            news_count += counter;

            jedis.set("news_count", news_count.toString());
        }



        public String md5(String base) {
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
