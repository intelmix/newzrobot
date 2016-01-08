package com.intelmix.newzrobot.crawler;

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
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Application implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

    @Autowired
    private FeedConfig cfg;

    @Autowired
    private FeedCrawler crawler;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                Application.class);
        application.setWebEnvironment(false);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext ctx =application.run(args);
        //Environment env = ctx.getEnvironment();
        //System.out.println("value of port is " + serverPort);
    }

    //TODO: use LogEntries if it does not need much overhead
    @Override
    public void run(String... args) {
        //System.out.println("xxxxxmongodb port is: " + String.valueOf(mongodbPort));
        //System.out.println("value of port is " + env.getProperty("server.port"));

        //System.out.println("all feed ids:");
        //System.out.println(cfg.getFeedIds());
        crawler.doCrawl();
        //System.out.println("value of port is " + serverPort);
    }
}
