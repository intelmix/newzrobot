package com.intelmix.newzrobot.server;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import redis.clients.jedis.Jedis;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.intelmix.newzrobot.server.data.*;

@RestController
public class MainController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

    @Autowired
    private GoogleUserRepository userRepository;
    
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public AuthResponse register(@RequestBody AuthRequest req) {
        String access_token = req.getToken();
        
        logger.info("Register request received: "+access_token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        User user = restTemplate.getForObject("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + access_token, User.class);

        GoogleUser gu = new GoogleUser(user.getId(), user.getName(), user.getGivenName(), user.getFamilyName(), 
                user.getLink(), user.getPicture(), user.getGender(), user.getLocale(), user.getEmail(), access_token);
        userRepository.save(gu);

        //Sample response:
        //{
        // "id": "108051250147721565705",
        //  "name": "Mahdi Mohammadi",
        //   "given_name": "Mahdi",
        //    "family_name": "Mohammadi",
        //     "link": "https://plus.google.com/+MahdiMohammadinasab",
        //      "picture": "https://lh3.googleusercontent.com/-SAD9UVfwQZU/AAAAAAAAAAI/AAAAAAAAAMA/r-4abH4SfD8/photo.jpg",
        //       "gender": "male",
        //       "email" : "dsadsadsA",
        //        "locale": "en"
        //        }
        //}
        //

        //TODO: make sure this token is issues for my application by clling: https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=?
        //expected result:
        //{
        //  "issued_to": "407408718192.apps.googleusercontent.com", 
        //    "user_id": "108051250147721565705", 
        //      "expires_in": 3573,  #is in seconds
        //        "access_type": "offline", 
        //          "audience": "407408718192.apps.googleusercontent.com", 
        //            "scope": "https://www.googleapis.com/auth/userinfo.profile"
        //            }
        //}
        //
        
        
        //now we have to save information to db

        return new AuthResponse(access_token);
    }

    @RequestMapping("/search/{query}")
    public List<NewsItem> search(@PathVariable("query") String query) {
        logger.info("Request received for search: "+query);

        List<NewsItem> allNews = news();
        List<NewsItem> result = new ArrayList<NewsItem>();

        for(int i=0;i<allNews.size();i++) {
            if ( allNews.get(i).getTitle().contains(query) ) {
                result.add(allNews.get(i));
            }
        }

        return result;
    }

    @RequestMapping("/newsCount")
    public ResponseEntity<String> newsCount() {
        logger.info("Request received for news count");
        
        List<NewsItem> result = new ArrayList<NewsItem>();
        DBCursor cursor = null;

        Jedis jedis = new Jedis("localhost");

        String count = jedis.get("news_count");

        if ( count == null ) count = "0";

        return new ResponseEntity<String>(String.valueOf(count), HttpStatus.OK);
    }

    @RequestMapping("/news")
    public List<NewsItem> news() {
        logger.info("Request received for news");

        List<NewsItem> result = new ArrayList<NewsItem>();
        DBCursor cursor = null;

        try {
            MongoClient mongo = new MongoClient( "localhost" , 27017 );
            DB db = mongo.getDB("data"); 
            DBCollection feed_entry = db.getCollection("feed_entry");

            cursor = feed_entry.find();
            int counter = 0;
            while(cursor.hasNext() && counter < 10) {
                counter++;

                BasicDBObject doc = (BasicDBObject)cursor.next();
                NewsItem ni = new NewsItem(doc.getString("t"), doc.getString("s"), doc.getString("u"), doc.getLong("e"));

                result.add(ni);
            }
        } catch(java.net.UnknownHostException ex) {
            logger.error("unknown host exception: "+ex.getMessage());
        } finally {
            if(cursor != null) cursor.close();
        }

        return result;
    }

}

